package br.com.bitzen.desafio.controller;

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

import br.com.bitzen.desafio.integration.domain.Artista;
import br.com.bitzen.desafio.service.ArtistaService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/artistas")
public class ArtistaController {

	@Autowired
	private ArtistaService artistaService;

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody Artista artista) {
		log.debug("ArtistaController: save {}", artista);
		return new ResponseEntity<>(artistaService.save(artista), HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<Object> listAll() {
		log.debug("ArtistaController: listAll");
		return new ResponseEntity<>(artistaService.listAll(), HttpStatus.OK);
	}
	
    @GetMapping("/paginated")
    public ResponseEntity<Page<Artista>> listAllPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
    	log.debug("ArtistaController: listAllPaginated");

        return new ResponseEntity<>(artistaService.listAllPaginated(page, size), HttpStatus.OK);
    }

	@GetMapping("/{id}")
	public ResponseEntity<Object> findById(@PathVariable Long id) {
		log.debug("ArtistaController: findById {}", id);
		return new ResponseEntity<>(artistaService.findById(id), HttpStatus.OK);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Object> update(@RequestBody Artista artistaAtualizado) {
		log.debug("ArtistaController: update {}", artistaAtualizado);
		return new ResponseEntity<>(artistaService.update(artistaAtualizado), HttpStatus.OK);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Object> delete(@PathVariable(value = "id", required = true) Long id) throws Exception {
		log.debug("ArtistaController: delete {}", id);
		
        artistaService.delete(id);
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

}
