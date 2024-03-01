package br.com.bitzen.desafio.business.service;

import java.util.List;
import java.util.Map;

import br.com.bitzen.desafio.integration.domain.Musica;

public interface IMusicaService extends IBaseService<Musica> {

	List<Map<String, Object>> listAllByIdArtista(Long idArtista);

	List<Map<String, Object>> listAllByIdAlbum(Long idAlbum, String orderBy);

}
