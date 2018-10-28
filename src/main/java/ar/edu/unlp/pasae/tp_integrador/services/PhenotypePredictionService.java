package ar.edu.unlp.pasae.tp_integrador.services;

import ar.edu.unlp.pasae.tp_integrador.dtos.PhenotypePredictionRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PhenotypePredictionResultDTO;

public interface PhenotypePredictionService {
	/**
	 * Predice el fenotipo de un paciente utilizando la informacion del analisis indicado y los datos del paciente
	 *
	 * @param predictionRequest
	 *
	 * @return dto el resultado
	 */
	PhenotypePredictionResultDTO predict(PhenotypePredictionRequestDTO predictionRequest);
}
