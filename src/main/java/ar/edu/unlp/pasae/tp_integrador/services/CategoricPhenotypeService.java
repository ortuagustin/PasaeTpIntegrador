package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;

public interface CategoricPhenotypeService {
	CategoricPhenotypeDTO find(Long id);
	CategoricPhenotypeDTO findByName(String name) throws EntityNotFoundException;
	Stream<CategoricPhenotypeDTO> list();
	CategoricPhenotypeDTO create(CategoricPhenotypeDTO Phenotype);
	Integer count();
	CategoricPhenotypeDTO update(CategoricPhenotypeDTO Phenotype);
	void delete(Long id);
}
