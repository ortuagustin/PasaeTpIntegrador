package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;

import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyRequestDTO;

public interface PathologyService {
	/**
	 * Devuelve una patologia dado su id
	 * @param pathologyId el id de la patologia
	 * @return dto con los datos de la patologia solicitada
	 */
	PathologyDTO find(Long pathologyId) throws EntityNotFoundException;

	/**
	 * Devuelve una patologia dado su nombre
	 * @param name  el nombre de la patologia
	 * @return dto con los datos de la patologia solicitada
	 */
	PathologyDTO findByName(String name) throws EntityNotFoundException;

	/**
	 * Devuelve un Stream con todos los patologias del sistema
	 * @return stream de patologias
	 */
	Stream<PathologyDTO> list();
	
	/**
	 * Devuelve un Paginado de patologiaas, de acuerdo a los parametros dados
	 * @param page el numero de pagina
	 * @param sizePerPage cantidad de elementos por pagina
	 * @param sortField el campo por el que se desea ordenar
	 * @param sortOrder ascendente o descendente
	 * @param search filtro
	 * @return pagina de paotologias
	 */
	Page<PathologyDTO> list(int page, int sizePerPage, String sortField, String sortOrder, String search);

	/**
	 * Crea una patologia
	 * @param pathology dto con los datos de la patologia
	 * @return dto con los datos de la patologia persistida
	 */
	PathologyDTO create(PathologyRequestDTO pathology);

	/**
	 * Devuelve la cantidad de patologias
	 * @return la cantidad de patologias
	 */
	Integer count();

	/**
	 * Actualiza una patologia
	 * @param pathologyId el id de la patologia a actualizar
	 * @param pathology dto con los datos a actualizar
	 * @return dto con los datos de la patologia actualizada
	 */
	PathologyDTO update(Long pathologyId, PathologyRequestDTO pathology);

	/**
	 * Elimina una patologia
	 * @param pathologyId el id de la patologia
	 */
	void delete(Long pathologyId) throws EntityNotFoundException;
}
