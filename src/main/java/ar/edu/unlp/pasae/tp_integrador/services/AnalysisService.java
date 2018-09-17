package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisRequestDTO;

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
	 * Devuelve un Stream con todos los analisis en estado Pendiente
	 *
	 * @return stream de analisis
	 */
	Stream<AnalysisDTO> listPending();

	/**
	 * Devuelve un Stream con todos los analisis en estado Borrador
	 *
	 * @return stream de analisis
	 */
	Stream<AnalysisDTO> listDraft();

	/**
	 * Devuelve un Stream con todos los analisis en estado Publicado
	 *
	 * @return stream de analisis
	 */
	Stream<AnalysisDTO> listPublished();

	/**
	 * Crea un analisis
	 *
	 * @param analysis dto con los datos del analisis
	 *
	 * @return dto con los datos del analisis persistido
	 */
	AnalysisDTO create(AnalysisRequestDTO analysis);

	/**
	 * Actualiza un analisis a estado Borrador
	 *
	 * @param analysisId el id del analisis
	 *
	 * @return dto con los datos del analisis actualizado
	 */
	AnalysisDTO draft(Long analysisId);

	/**
	 * Actualiza un analisis a estado Publicado
	 *
	 * @param analysisId el id del analisis
	 *
	 * @return dto con los datos del analisis actualizado
	 */
	AnalysisDTO publish(Long analysisId);

}
