package ar.edu.unlp.pasae.tp_integrador.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.DraftAnalysisRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PendingAnalysisRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.exceptions.GenotypeDecoderException;

public interface AnalysisService {
	/**
	 * Devuelve un analisis dado su id
	 *
	 * @param analysisId el id del analisis
	 *
	 * @return dto con los datos del analisis solicitado
	 */
	AnalysisDTO find(Long analysisId) throws EntityNotFoundException;

	/**
	 * Crea un analisis
	 *
	 * @param analysis dto con los datos del analisis
	 *
	 * @return dto con los datos del analisis persistido
	 */
	AnalysisDTO pending(PendingAnalysisRequestDTO analysis) throws GenotypeDecoderException;

	/**
	 * Actualiza un analisis a estado Borrador
	 *
	 * @param analysisId el id del analisis
	 *
	 * @return dto con los datos del analisis actualizado
	 */
	AnalysisDTO draft(DraftAnalysisRequestDTO analysis) throws GenotypeDecoderException;

	/**
	 * Actualiza un analisis a estado Publicado
	 *
	 * @param analysisId el id del analisis
	 *
	 * @return dto con los datos del analisis actualizado
	 */
	AnalysisDTO publish(Long analysisId);

	/**
	 * Devuelve un Paginado de analisis, de acuerdo a los parametros dados
	 *
	 * @param page el numero de pagina
	 * @param sizePerPage cantidad de elementos por pagina
	 * @param sortField el campo por el que se desea ordenar
	 * @param sortOrder ascendente o descendente
	 * @param search filtro
	 *
	 * @return pagina de analisis
	 */
	Page<AnalysisDTO> list(int page, int sizePerPage, String sortField, String sortOrder, String search);

	/**
	 * Devuelve un Paginado de analisis, de acuerdo a los parametros dados
	 *
	 * @param page el numero de pagina
	 * @param sizePerPage cantidad de elementos por pagina
	 * @param sortField el campo por el que se desea ordenar
	 * @param sortOrder ascendente o descendente
	 * @param search filtro
	 *
	 * @return pagina de analisis
	 */
	Page<AnalysisDTO> listDraft(int page, int sizePerPage, String sortField, String sortOrder, String search);

	/**
	 * Devuelve un Paginado de analisis, de acuerdo a los parametros dados
	 *
	 * @param page el numero de pagina
	 * @param sizePerPage cantidad de elementos por pagina
	 * @param sortField el campo por el que se desea ordenar
	 * @param sortOrder ascendente o descendente
	 * @param search filtro
	 *
	 * @return pagina de analisis
	 */
	Page<AnalysisDTO> listPublished(int page, int sizePerPage, String sortField, String sortOrder, String search);

	/**
	 * Elimina un analisis
	 * @param analysisId el id del analisis
	 */
	void delete(Long analysisId) throws EntityNotFoundException;
}
