package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;

import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;

public interface NumericPhenotypeService {
	/**
	 * Devuelve un fenotipo dado su id
	 *
	 * @param phenotypeId el id del fenotipo
	 *
	 * @return dto con los datos del fenotipo solicitado
	 */
	NumericPhenotypeDTO find(Long phenotypeId) throws EntityNotFoundException;

	/**
	 * Devuelve un fenotipo dado su nombre
	 *
	 * @param name el nombre del fenotipo
	 *
	 * @return dto con los datos del fenotipo solicitado
	 */
	NumericPhenotypeDTO findByName(String name) throws EntityNotFoundException;
	

	/**
	 * Devuelve un Stream con todos los fenotipos del sistema
	 *
	 * @return stream de fenotipos
	 */
	//Stream<NumericPhenotypeDTO> list(int page, int sizePerPage, String sortField, String sortOrder, String search);
	Page<NumericPhenotype> list(int page, int sizePerPage, String sortField, String sortOrder, String search);

	/**
	 * Crea un fenotipo
	 *
	 * @param phenotype dto con los datos del fenotipo
	 *
	 * @return dto con los datos del fenotipo persistido
	 */
	NumericPhenotypeDTO create(NumericPhenotypeDTO phenotype);

	/**
	 * Devuelve la cantidad de fenotipos
	 *
	 * @return la cantidad de fenotipos
	 */
	Integer count();

	/**
	 * Actualiza un fenotipo
	 *
	 * @param phenotype dto con los datos a actualizar
	 *
	 * @return dto con los datos del fenotipo actualizado
	 */
	NumericPhenotypeDTO update(NumericPhenotypeDTO phenotype);

	/**
	 * Elimina un fenotipo
	 *
	 * @param phenotypeId el id del fenotipo
	 */
	void delete(Long phenotypeId) throws EntityNotFoundException;
}
