package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisDTO;

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
	AnalysisDTO create(AnalysisDTO analysis);

	/**
	 * Actualiza un analisis
	 *
	 * @param analysisId el id del analisis a publicar
	 *
	 * @return dto con los datos del analisis actualizado
	 */
	AnalysisDTO publish(Long analysisId);
}
