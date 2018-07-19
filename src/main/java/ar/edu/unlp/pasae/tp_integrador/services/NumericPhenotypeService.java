package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;

public interface NumericPhenotypeService {
	NumericPhenotypeDTO find(Long id);
	NumericPhenotypeDTO findByName(String name) throws EntityNotFoundException;
	Stream<NumericPhenotypeDTO> list();
	NumericPhenotypeDTO create(NumericPhenotypeDTO Phenotype);
	Integer count();
	NumericPhenotypeDTO update(NumericPhenotypeDTO Phenotype);
	void delete(Long id);
}
