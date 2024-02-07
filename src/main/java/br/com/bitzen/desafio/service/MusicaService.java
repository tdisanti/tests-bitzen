package br.com.bitzen.desafio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.bitzen.desafio.business.service.IMusicaService;
import br.com.bitzen.desafio.integration.domain.Musica;
import br.com.bitzen.desafio.integration.repository.MusicaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MusicaService implements IMusicaService {

    private final MusicaRepository musicaRepository;

    @Autowired
    public MusicaService(MusicaRepository musicaRepository) {
        this.musicaRepository = musicaRepository;
    }

    @Override
    public Musica save(Musica musica) {
        return musicaRepository.save(musica);
    }

    @Override
    public List<Musica> listAll() {
        return musicaRepository.findAll();
    }
    
	@Override
	public Page<Musica> listAllPaginated(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return musicaRepository.findAll(pageable);
	}

	@Override
    public Optional<Musica> findById(Long id) {
        return musicaRepository.findById(id);
    }

	@Override
    public Musica update(Musica musicaAtualizada) {
        Optional<Musica> musicaOptional = musicaRepository.findById(musicaAtualizada.getId());

        if (musicaOptional.isPresent()) {
        	Musica musica = musicaOptional.get();
        	musica.setTitle(musicaAtualizada.getTitle());
        	musica.setDurationSeconds(musicaAtualizada.getDurationSeconds());
            return musicaRepository.save(musicaAtualizada);
        } else {
			String errorMessage = "Música não encontrada com o ID: " + musicaAtualizada.getId();
			log.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
        }
    }

	@Override
    public void delete(Long id) {
        musicaRepository.deleteById(id);
    }
}
