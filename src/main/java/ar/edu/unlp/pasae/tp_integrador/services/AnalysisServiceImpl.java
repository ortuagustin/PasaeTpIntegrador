package ar.edu.unlp.pasae.tp_integrador.services;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Analysis;
import ar.edu.unlp.pasae.tp_integrador.entities.AnalysisState;
import ar.edu.unlp.pasae.tp_integrador.entities.Pathology;
import ar.edu.unlp.pasae.tp_integrador.entities.Patient;
import ar.edu.unlp.pasae.tp_integrador.entities.Phenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.Analysis.AnalysisBuilder;
import ar.edu.unlp.pasae.tp_integrador.repositories.AnalysisRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.PathologyRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.PatientRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.PhenotypeRepository;

@Service
public class AnalysisServiceImpl implements AnalysisService {
	@Autowired
	private AnalysisRepository analysisRepository;
	@Autowired
	private PathologyRepository pathologyRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private PhenotypeRepository phenotypeRepository;

	@Override
	public AnalysisDTO find(Long analysisId) throws EntityNotFoundException {
		final Analysis analysis = this.findAnalysisById(analysisId);

		return this.toDTO(analysis);
	}

	@Override
	public Stream<AnalysisDTO> listPending() {
		return this.listByState(AnalysisState.PENDING);
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
	public AnalysisDTO create(AnalysisRequestDTO analysis) {
		Analysis entity = this.buildAnalysis(analysis);

		return this.save(entity);
	}

	@Override
	public AnalysisDTO draft(Long analysisId) {
		final Analysis entity = this.findAnalysisById(analysisId);
		entity.setState(AnalysisState.DRAFT);

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

		return this.toDTO(analysis);
	}

	private Analysis buildAnalysis(AnalysisRequestDTO analysis) {
		final AnalysisBuilder builder = Analysis.builder();

		 return builder
		 	.addDate(new Date())
			.addState(AnalysisState.PENDING)
			.addPatients(this.findPatients(analysis.getPatientsIds()))
			.addPathology(this.findPathology(analysis.getPathologyId()))
			.addSnps(analysis.getSnps())
			.addPhenotype(this.findPhenotype(analysis.getPhenotypeId()))
			.createAnalysis();
	}

	private Pathology findPathology(Long pathologyId) {
		final Pathology pathology = this.pathologyRepository.findById(pathologyId)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No pathology found with Id {0}", pathologyId)));

		return pathology;
	}

	private Phenotype findPhenotype(Long phenotypeId) {
		final Phenotype phenotype = this.phenotypeRepository.findById(phenotypeId)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No phenotype found with Id {0}", phenotypeId)));

		return phenotype;
	}

	private Set<Patient> findPatients(Set<Long> patientsIds) {
		return this.patientRepository.findAllById(patientsIds)
			.stream()
			.collect(Collectors.toSet());
	}

	private Stream<AnalysisDTO> listByState(AnalysisState state) {
		return this.getAnalysisRepository().findByState(state).stream().map(each -> this.toDTO(each));
	}

	private Analysis findAnalysisById(Long analysisId) throws EntityNotFoundException {
		final Analysis analysis = this.getAnalysisRepository().findById(analysisId)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Analysis found with Id {0}", analysisId)));

		return analysis;
	}

	private Set<Long> getPatientIds(Analysis entity) {
		return entity.getPatients()
			.stream()
			.map(each -> each.getId())
			.collect(Collectors.toSet());
	}

	private AnalysisDTO toDTO(Analysis entity) {
		AnalysisDTO dto = new AnalysisDTO();
		dto.setId(entity.getId());
		dto.setDate(entity.getDate());
		dto.setState(entity.getState());
		dto.setSnps(entity.getSnps());
		dto.setPatients(this.getPatientIds(entity));
		dto.setPathologyId(entity.getPathology().getId());
		dto.setPhenotypeKind(entity.getPhenotype().getKind());
		dto.setPhenotypeId(entity.getPhenotype().getId());

		return dto;
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
