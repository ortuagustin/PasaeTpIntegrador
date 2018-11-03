package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.Collection;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.SnpDTO;
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
	AnalysisDTO create(AnalysisRequestDTO analysis) throws GenotypeDecoderException;

	/**
	 * Actualiza un analisis a estado Borrador
	 *
	 * @param analysisId el id del analisis
	 * @param snps coleccion de snps seleccionados por el cientifico
	 *
	 * @return dto con los datos del analisis actualizado
	 */
	AnalysisDTO draft(Long analysisId, Collection<SnpDTO> snps);

	/**
	 * Actualiza un analisis a estado Publicado
	 *
	 * @param analysisId el id del analisis
	 *
	 * @return dto con los datos del analisis actualizado
	 */
	AnalysisDTO publish(Long analysisId);

	/**
	 * Devuelve un Paginado de pacientes, de acuerdo a los parametros dados
	 * @param page el numero de pagina
	 * @param sizePerPage cantidad de elementos por pagina
	 * @param sortField el campo por el que se desea ordenar
	 * @param sortOrder ascendente o descendente
	 * @param search filtro
	 * @return pagina de paotologias
	 */
	Page<AnalysisDTO> list(int page, int sizePerPage, String sortField, String sortOrder, String search);
	
	/**
	 * Elimina un analisis
	 * @param analysisId el id del analisis
	 */
	void delete(Long analysisId) throws EntityNotFoundException;
}
