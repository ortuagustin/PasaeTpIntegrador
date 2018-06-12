package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import ar.edu.unlp.pasae.tp_integrador.dtos.PhenotypeDTO;

public interface PhenotypeService {
	PhenotypeDTO find(Long id);
	PhenotypeDTO findByName(String name) throws EntityNotFoundException;
	Stream<PhenotypeDTO> list();
	PhenotypeDTO create(PhenotypeDTO Phenotype);
	Integer count();
	PhenotypeDTO update(PhenotypeDTO Phenotype);
	void delete(Long id);
}
