package br.com.bitzen.desafio.integration.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.bitzen.desafio.integration.domain.Musica;

public interface MusicaRepository extends JpaRepository<Musica, Long> {

	@Query("SELECT m FROM Musica m JOIN FETCH m.album a JOIN FETCH a.artista WHERE a.artista.id = :idArtista")
	List<Musica> listAllByIdArtista(@Param("idArtista") Long idArtista);

}