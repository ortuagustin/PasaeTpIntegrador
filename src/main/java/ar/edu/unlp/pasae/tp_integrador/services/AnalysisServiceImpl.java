package ar.edu.unlp.pasae.tp_integrador.services;

import java.text.MessageFormat;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.unlp.pasae.tp_integrador.transformers.Transformer;

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Analysis;
import ar.edu.unlp.pasae.tp_integrador.entities.AnalysisState;
import ar.edu.unlp.pasae.tp_integrador.entities.Analysis.AnalysisBuilder;
import ar.edu.unlp.pasae.tp_integrador.repositories.AnalysisRepository;

@Service
public class AnalysisServiceImpl implements AnalysisService {
	@Autowired
	private AnalysisRepository analysisRepository;
	@Autowired
	private Transformer<Analysis, AnalysisDTO> transformer;

	@Override
	public AnalysisDTO find(Long analysisId) throws EntityNotFoundException {
		final Analysis analysis = this.findAnalysisById(analysisId);

		return this.getTransformer().toDTO(analysis);
	}

	@Override
	public Stream<AnalysisDTO> listDraft() {
		return this.listByState(AnalysisState.DRAFT);
	}

	@Override
	public Stream<AnalysisDTO> listPublished() {
		return this.listByState(AnalysisState.PUBLISHED);
	}

	@Override
	public AnalysisDTO create(AnalysisDTO analysis) {
		Analysis entity = this.buildAnalysis(analysis);

		return this.save(entity);
	}

	@Override
	public AnalysisDTO publish(Long analysisId) {
		final Analysis entity = this.findAnalysisById(analysisId);
		entity.setState(AnalysisState.PUBLISHED);

		return this.save(entity);
	}

	private AnalysisDTO save(Analysis analysis) {
		analysis = this.getAnalysisRepository().save(analysis);

		return this.getTransformer().toDTO(analysis);
	}

	private Analysis buildAnalysis(AnalysisDTO analysis) {
		final AnalysisBuilder builder = Analysis.builder();

		 return builder
		 	.addDate(analysis.getDate())
			.addState(analysis.getState())
			.createAnalysis();
	}

	private Stream<AnalysisDTO> listByState(AnalysisState state) {
		return this.getAnalysisRepository().findByState(state).stream().map(each -> this.getTransformer().toDTO(each));
	}

	private Analysis findAnalysisById(Long analysisId) throws EntityNotFoundException {
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

	/**
	 * @return the transformer
	 */
	public Transformer<Analysis, AnalysisDTO> getTransformer() {
		return transformer;
	}

	/**
	 * @param transformer the transformer to set
	 */
	public void setTransformer(Transformer<Analysis, AnalysisDTO> transformer) {
		this.transformer = transformer;
	}
}
