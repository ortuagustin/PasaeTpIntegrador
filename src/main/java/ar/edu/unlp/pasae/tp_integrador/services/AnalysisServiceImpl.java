package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ar.edu.unlp.pasae.tp_integrador.transformers.Transformer;

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Analysis;
import ar.edu.unlp.pasae.tp_integrador.entities.AnalysisState;
import ar.edu.unlp.pasae.tp_integrador.repositories.AnalysisRepository;

@Service
public class AnalysisServiceImpl implements AnalysisService {
	@Autowired
	private AnalysisRepository repository;
	@Autowired
	private Transformer<Analysis, AnalysisDTO> transformer;

	@Override
	public AnalysisDTO find(Long id) {
		return this.getTransformer().toDTO(this.getRepository().findById(id).get());
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
	public AnalysisDTO create(AnalysisDTO analysisDTO) {
		Analysis analysis = this.getRepository().save(this.getTransformer().toEntity(analysisDTO));

		return this.getTransformer().toDTO(analysis);
	}

	@Override
	public AnalysisDTO publish(AnalysisDTO analysisDTO) {
		Analysis analysis = this.getRepository().findById(analysisDTO.getId()).get();
		analysis.setState(AnalysisState.PUBLISHED);
		this.getRepository().save(analysis);

		return this.getTransformer().toDTO(analysis);
	}

	protected Stream<AnalysisDTO> listByState(AnalysisState state) {
		return this.getRepository().findByState(state).stream().map(each -> this.getTransformer().toDTO(each));
	}

	/**
	 * @return the repository
	 */
	public AnalysisRepository getRepository() {
		return repository;
	}

	/**
	 * @param repository the repository to set
	 */
	public void setRepository(AnalysisRepository repository) {
		this.repository = repository;
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
