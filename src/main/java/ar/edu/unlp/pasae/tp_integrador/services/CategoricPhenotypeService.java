package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;

public interface CategoricPhenotypeService {
	/**
	 * Devuelve un fenotipo dado su id
	 *
	 * @param phenotypeId el id del fenotipo
	 *
	 * @return dto con los datos del fenotipo solicitado
	 */
	CategoricPhenotypeDTO find(Long phenotypeId) throws EntityNotFoundException;

	/**
	 * Devuelve un fenotipo dado su dni
	 *
	 * @param name el nombre del fenotipo
	 *
	 * @return dto con los datos del fenotipo solicitado
	 */
	CategoricPhenotypeDTO findByName(String name) throws EntityNotFoundException;

	/**
	 * Devuelve un Stream con todos los fenotipos del sistema
	 *
	 * @return stream de fenotipos
	 */
	Stream<CategoricPhenotypeDTO> list();

	/**
	 * Crea un fenotipo
	 *
	 * @param phenotype dto con los datos del fenotipo
	 *
	 * @return dto con los datos del fenotipo persistido
	 */
	CategoricPhenotypeDTO create(CategoricPhenotypeDTO phenotype);

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
	CategoricPhenotypeDTO update(CategoricPhenotypeDTO phenotype);

	/**
	 * Elimina un fenotipo
	 *
	 * @param phenotypeId el id del fenotipo
	 */
	void delete(Long phenotypeId) throws EntityNotFoundException;
}
