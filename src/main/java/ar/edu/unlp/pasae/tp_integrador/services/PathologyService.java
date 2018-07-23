package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyRequestDTO;

public interface PathologyService {
	/**
	 * Devuelve una patologia dado su id
	 *
	 * @param pathologyId el id de la patologia
	 *
	 * @return dto con los datos de la patologia solicitada
	 */
	PathologyDTO find(Long pathologyId) throws EntityNotFoundException;

	/**
	 * Devuelve una patologia dado su nombre
	 *
	 * @param name  el nombre de la patologia
	 *
	 * @return dto con los datos de la patologia solicitada
	 */
	PathologyDTO findByName(String name) throws EntityNotFoundException;

	/**
	 * Devuelve un Stream con todos los patologias del sistema
	 *
	 * @return stream de patologias
	 */
	Stream<PathologyDTO> list();

	/**
	 * Crea una patologia
	 *
	 * @param pathology dto con los datos de la patologia
	 *
	 * @return dto con los datos de la patologia persistida
	 */
	PathologyDTO create(PathologyRequestDTO pathology);

	/**
	 * Devuelve la cantidad de patologias
	 *
	 * @return la cantidad de patologias
	 */
	Integer count();

	/**
	 * Actualiza una patologia
	 *
	 * @param pathology dto con los datos a actualizar
	 *
	 * @return dto con los datos de la patologia actualizada
	 */
	PathologyDTO update(PathologyRequestDTO pathology);

	/**
	 * Elimina una patologia
	 *
	 * @param pathologyId el id de la patologia
	 */
	void delete(Long pathologyId) throws EntityNotFoundException;
}
