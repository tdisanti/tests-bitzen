package br.com.bitzen.desafio.business.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

public interface IBaseService<T> {

	T save(T objectToSave);
	
	List<T> listAll();
	
	Page<T> listAllPaginated(int page, int size);
	
	Optional<T> findById(Long id);
	
	T update(T objectToSave);

	void delete(Long id);
	
}
