package br.com.bitzen.desafio.integration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bitzen.desafio.integration.domain.Musica;

public interface MusicaRepository extends JpaRepository<Musica, Long> {
	
}