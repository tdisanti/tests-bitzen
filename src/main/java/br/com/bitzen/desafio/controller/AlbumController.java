package br.com.bitzen.desafio.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bitzen.desafio.integration.domain.Album;
import br.com.bitzen.desafio.service.AlbumService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/album/v1")
public class AlbumController {

	@Autowired
	private AlbumService albumService;

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody Album album) {
		log.debug("AlbumController: save {}", album);
		return new ResponseEntity<>(albumService.save(album), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Object> listAll() {
		log.debug("AlbumController: listAll");
		
		return new ResponseEntity<>(albumService.listAll(), HttpStatus.OK);
	}

	@GetMapping("/paginated")
	public ResponseEntity<Page<Album>> listAllPaginated(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		log.debug("AlbumController: listAllPaginated");

		return new ResponseEntity<>(albumService.listAllPaginated(page, size), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Album> findById(@PathVariable Long id) {
		log.debug("AlbumController: findById {}", id);

		Optional<Album> albumOptional = albumService.findById(id);
		return albumOptional.map(album -> new ResponseEntity<>(album, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@RequestBody Album albumAtualizado) {
		log.debug("AlbumController: update {}", albumAtualizado);
		return new ResponseEntity<>(albumService.update(albumAtualizado), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable(value = "id", required = true) Long id) throws Exception {
		log.debug("AlbumController: delete {}", id);

		albumService.delete(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
