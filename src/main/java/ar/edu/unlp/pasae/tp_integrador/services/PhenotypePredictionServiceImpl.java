package ar.edu.unlp.pasae.tp_integrador.services;

import java.text.MessageFormat;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.PhenotypePredictionRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PhenotypePredictionResultDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Analysis;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.Phenotype;
import ar.edu.unlp.pasae.tp_integrador.repositories.AnalysisRepository;

@Service
public class PhenotypePredictionServiceImpl implements PhenotypePredictionService {
	@Autowired
	private AnalysisRepository analysisRepository;

	@Override
	public PhenotypePredictionResultDTO predict(PhenotypePredictionRequestDTO predictionRequest) {
		final Analysis analysis = this.findAnalysis(predictionRequest.getAnalysisId());
		final Phenotype phenotype = analysis.getPhenotype();
		final String predictedValue;

		// TODO: deberiamos obtener este valor desde Python, por ahora lo que se hace es:
		// para los fenotipos categoricos devuelve como resultado el primer valor de la lista
		// para los fenotios numericos devuelve un valor random
		if (phenotype instanceof CategoricPhenotype) {
			predictedValue = ((CategoricPhenotype)phenotype).getValues().values().stream().findFirst().get();
		} else {
			predictedValue = Double.toString(Math.random());
		}

		PhenotypePredictionResultDTO result = new PhenotypePredictionResultDTO(phenotype.getId(), phenotype.getKind(), predictedValue);

		return result;
	}

	private Analysis findAnalysis(Long analysisId) throws EntityNotFoundException {
		final Analysis analysis = this.getAnalysisRepository().findById(analysisId)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Analysis found with Id {0}", analysisId)));

		return analysis;
	}

	/**
	 * @return the analysisRepository
	 */
	public AnalysisRepository getAnalysisRepository() {
		return analysisRepository;
	}

	/**
	 * @param analysisRepository the analysisRepository to set
	 */
	public void setAnalysisRepository(AnalysisRepository analysisRepository) {
		this.analysisRepository = analysisRepository;
	}
}
