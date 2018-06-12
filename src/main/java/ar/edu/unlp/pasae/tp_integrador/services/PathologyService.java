package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyDTO;

public interface PathologyService {
	PathologyDTO find(Long id);
	PathologyDTO findByName(String name) throws EntityNotFoundException;
	Stream<PathologyDTO> list();
	PathologyDTO create(PathologyDTO Pathology);
	Integer count();
	PathologyDTO update(PathologyDTO Pathology);
	void delete(Long id);
}
