package br.com.bitzen.desafio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.bitzen.desafio.business.service.IAlbumService;
import br.com.bitzen.desafio.integration.domain.Album;
import br.com.bitzen.desafio.integration.repository.AlbumRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AlbumService implements IAlbumService {

    private final AlbumRepository albumRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public Album save(Album album) {
        return albumRepository.save(album);
    }

    @Override
    public List<Album> listAll() {
        return albumRepository.findAll();
    }
    
	@Override
	public Page<Album> listAllPaginated(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		return albumRepository.findAll(pageable);
	}

	@Override
    public Optional<Album> findById(Long id) {
        return albumRepository.findById(id);
    }

	@Override
    public Album update(Album albumAtualizado) {
        Optional<Album> albumOptional = albumRepository.findById(albumAtualizado.getId());

        if (albumOptional.isPresent()) {
        	
			Album album = albumOptional.get();
			album.setTitle(albumAtualizado.getTitle());
			album.setYear(albumAtualizado.getYear());
			return albumRepository.save(album);
        } else {
			String errorMessage = "Álbum não encontrado com o ID: " + albumAtualizado.getId();
			log.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
        }
    }

	@Override
    public void delete(Long id) {
        albumRepository.deleteById(id);
    }
}