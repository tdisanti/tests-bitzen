package br.com.bitzen.desafio.service;

import java.time.Year;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.bitzen.desafio.business.service.IAlbumService;
import br.com.bitzen.desafio.business.service.IArtistaService;
import br.com.bitzen.desafio.exception.BitzenServiceException;
import br.com.bitzen.desafio.integration.domain.Album;
import br.com.bitzen.desafio.integration.domain.Artista;
import br.com.bitzen.desafio.integration.repository.AlbumRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AlbumService implements IAlbumService {

	@Autowired
	private IArtistaService artistaService;
	
    private final AlbumRepository albumRepository;
    
    @Autowired
    public AlbumService(AlbumRepository albumRepository) {
        this.albumRepository = albumRepository;
    }

    @Override
    public Album save(Album album) {
    	validate(album);
    	
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
		validate(albumAtualizado);
		
        Optional<Album> albumOptional = albumRepository.findById(albumAtualizado.getId());

        if (albumOptional.isPresent()) {
        	
			Album album = albumOptional.get();
			album.setTitle(albumAtualizado.getTitle());
			album.setReleaseYear(albumAtualizado.getReleaseYear());
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
	
	private void validate(Album album) {
		if(album == null) throw new BitzenServiceException("Album não pode ser nulo");
		
		if(album.getReleaseYear() < Year.now().getValue()) {
			 throw new BitzenServiceException("Ano de lançamento de um Álbum não pode ser uma data posterior a atual");
		}
		
		if(album.getArtista() == null || album.getArtista().getId() == null) throw new BitzenServiceException("Artista não pode ser nulo");
		
		Optional<Artista> artistaOpt = artistaService.findById(album.getArtista().getId());
		if(!artistaOpt.isPresent()) throw new NoSuchElementException("Artista não encontrado");
	}
	
}
