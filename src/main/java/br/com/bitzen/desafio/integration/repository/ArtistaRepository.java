package br.com.bitzen.desafio.integration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bitzen.desafio.integration.domain.Artista;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {
	
}