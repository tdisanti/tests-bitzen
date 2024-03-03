package br.com.bitzen.desafio.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.bitzen.desafio.business.service.IMusicaService;
import br.com.bitzen.desafio.enumeration.OrderByEnum;
import br.com.bitzen.desafio.exception.BitzenServiceException;
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
    	validate(musica);
    	
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
		validate(musicaAtualizada);
		
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

	@Override
	public List<Map<String, Object>> listAllByIdArtista(Long idArtista) {
		return returnList(musicaRepository.findAllByIdArtista(idArtista));
	}
	
	@Override
	public List<Map<String, Object>> listAllByIdAlbum(Long idAlbum, String orderBy) {
		if (OrderByEnum.TITLE.name().equals(orderBy)) {
			return returnList(musicaRepository.findAllByAlbumIdOrderByTitle(idAlbum));
		} else if (OrderByEnum.TRACK_NUMBER.name().equals(orderBy)) {
			return returnList(musicaRepository.findAllByAlbumIdOrderByTrackNumber(idAlbum));
		}
		return Collections.emptyList();
	}
	
	private List<Map<String, Object>> returnList(List<Musica> listMusica) {
		List<Map<String, Object>> result = new ArrayList<>();
		for (Musica musica : listMusica) {
			Map<String, Object> musicaMap = new HashMap<>();
			musicaMap.put("id", musica.getId());
			musicaMap.put("title", musica.getTitle());
			musicaMap.put("durationSeconds", musica.getDurationSeconds());
			musicaMap.put("trackNumber", musica.getTrackNumber());
			
			Map<String, Object> albumMap = new HashMap<>();
			albumMap.put("id", musica.getAlbum().getId());
			albumMap.put("title", musica.getAlbum().getTitle());
			musicaMap.put("album", albumMap);
			result.add(musicaMap);
		}
		
		return result;
	}
	
	private void validate(Musica musica) {
		if (musica == null) throw new BitzenServiceException("Música não pode ser nula");
		
		if (musica.getTrackNumber() <= 0) throw new BitzenServiceException("Número de faixa deve ser maior que zero");
		
		if (musica.getDurationMinutes() < 0 || musica.getDurationMinutes() > 59 || musica.getDurationSeconds() < 0
				|| musica.getDurationSeconds() > 59)
			throw new BitzenServiceException("A duração das músicas deve ter minutos e segundos validos");
		
	}
	
}
