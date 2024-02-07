package br.com.bitzen.desafio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.bitzen.desafio.business.service.IArtistaService;
import br.com.bitzen.desafio.integration.domain.Artista;
import br.com.bitzen.desafio.integration.repository.ArtistaRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ArtistaService implements IArtistaService {

	private final ArtistaRepository artistaRepository;

	@Autowired
	public ArtistaService(ArtistaRepository artistaRepository) {
		this.artistaRepository = artistaRepository;
	}

	@Override
	public Artista save(Artista artista) {
		return artistaRepository.save(artista);
	}

	@Override
	public List<Artista> listAll() {
		return artistaRepository.findAll();
	}

	@Override
	public Page<Artista> listAllPaginated(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return artistaRepository.findAll(pageable);
	}

	@Override
	public Optional<Artista> findById(Long id) {
		return artistaRepository.findById(id);
	}

	@Override
	public Artista update(Artista artistaAtualizado) {
		Optional<Artista> artistaOptional = artistaRepository.findById(artistaAtualizado.getId());

		if (artistaOptional.isPresent()) {
			Artista artista = artistaOptional.get();
			artista.setName(artistaAtualizado.getName());
			artista.setNationality(artistaAtualizado.getNationality());
			artista.setWebsite(artistaAtualizado.getWebsite());
			artista.setProfileImage(artistaAtualizado.getProfileImage());
			return artistaRepository.save(artista);
		} else {
			String errorMessage = "Artista não encontrado com o ID: " + artistaAtualizado.getId();
			log.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}
	}

	@Override
	public void delete(Long id) {
		artistaRepository.deleteById(id);
	}

}
