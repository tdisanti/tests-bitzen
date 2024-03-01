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

import br.com.bitzen.desafio.integration.domain.Musica;
import br.com.bitzen.desafio.service.MusicaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/musica/v1")
public class MusicaController {

	@Autowired
	private MusicaService musicaService;

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody Musica musica) {
		log.debug("MusicaController: save {}", musica);
		return new ResponseEntity<>(musicaService.save(musica), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Object> listAll() {
		log.debug("MusicaController: listAll");
		
		return new ResponseEntity<>(musicaService.listAll(), HttpStatus.OK);
	}

	@GetMapping("/paginated")
	public ResponseEntity<Page<Musica>> listAllPaginated(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		log.debug("MusicaController: listAllPaginated");

		return new ResponseEntity<>(musicaService.listAllPaginated(page, size), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Musica> findById(@PathVariable Long id) {
		log.debug("MusicaController: findById {}", id);

		Optional<Musica> musicaOptional = musicaService.findById(id);
		return musicaOptional.map(musica -> new ResponseEntity<>(musica, HttpStatus.OK))
				.orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@RequestBody Musica musicaAtualizada) {
		log.debug("MusicaController: update {}", musicaAtualizada);
		return new ResponseEntity<>(musicaService.update(musicaAtualizada), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable(value = "id", required = true) Long id) throws Exception {
		log.debug("MusicaController: delete {}", id);

		musicaService.delete(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
    @GetMapping("/listByArtista/{idArtista}/")
    public ResponseEntity<Object> listAllByIdArtista(@PathVariable Long idArtista) {
    	log.debug("MusicaController: listAllByIdArtista {}", idArtista);
    	
        return new ResponseEntity<>(musicaService.listAllByIdArtista(idArtista), HttpStatus.OK);
    }

}
