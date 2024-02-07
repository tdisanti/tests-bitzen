package br.com.bitzen.desafio.integration.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bitzen.desafio.integration.domain.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
	
}