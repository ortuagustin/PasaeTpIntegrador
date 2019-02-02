package ar.edu.unlp.pasae.tp_integrador.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisGroupDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PendingAnalysisRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.DraftAnalysisRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.SnpDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Analysis;
import ar.edu.unlp.pasae.tp_integrador.entities.Analysis.AnalysisBuilder;
import ar.edu.unlp.pasae.tp_integrador.entities.AnalysisGroup;
import ar.edu.unlp.pasae.tp_integrador.entities.AnalysisState;
import ar.edu.unlp.pasae.tp_integrador.entities.Patient;
import ar.edu.unlp.pasae.tp_integrador.entities.Phenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.Snp;
import ar.edu.unlp.pasae.tp_integrador.exceptions.GenotypeDecoderException;
import ar.edu.unlp.pasae.tp_integrador.repositories.AnalysisRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.PatientRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.PhenotypeRepository;
import ar.edu.unlp.pasae.tp_integrador.transformers.Transformer;

@Service
public class AnalysisServiceImpl implements AnalysisService {
	@Autowired
	private AnalysisRepository analysisRepository;
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private PhenotypeRepository phenotypeRepository;
	@Autowired
	private Transformer<Patient, PatientDTO> patientsTransformer;
	@Autowired
	private GenotypeDecoderService genotypeDecoderService;

	@Override
	public AnalysisDTO find(Long analysisId) throws EntityNotFoundException {
		final Analysis analysis = this.findAnalysisById(analysisId);

		return this.toDTO(analysis);
	}
	
	/**
	 * Llama al script en Python para obtener el p-valor o el valor estadistico
	 * para un analisis
	 * @param field 'pvalue' | 'statistical' Para saber que se le solicita al script de python
	 * @return Salida del script
	 * @throws IOException 
	 * @throws URISyntaxException 
	 */
	private String callPythonScript(String field) throws IOException, URISyntaxException {
		String pythonScriptURL = this.getClass().getClassLoader().getResource("random_generator.py").toURI().getPath();
		Process process = Runtime.getRuntime().exec("python " + pythonScriptURL + " " + field);
		InputStream input = process.getInputStream();
		java.util.Scanner scanner = new java.util.Scanner(input);
		String response = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
		scanner.close();
		return response;
	}

	/**
	 * Obtiene el P-valor
	 * @return P-valor
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private Double getPValue() throws NumberFormatException, IOException, URISyntaxException {
		return Double.parseDouble(this.callPythonScript("pvalue"));
	}

	/**
	 * Obtiene el stadistico
	 * @return valor estadistico
	 * @throws NumberFormatException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private Double getStatistical() throws NumberFormatException, IOException, URISyntaxException {
		return Double.parseDouble(this.callPythonScript("statistical"));
	}

	@Override
	public AnalysisDTO pending(PendingAnalysisRequestDTO analysis) throws GenotypeDecoderException, NumberFormatException, IOException, URISyntaxException {
		Analysis entity = this.buildAnalysis(analysis);
		AnalysisDTO dto = this.toDTO(entity);
		final Collection<String> snps = this.getGenotypeDecoderService().decodeSnps(analysis.getSnps());

		for (String each : snps) {
			SnpDTO snp = new SnpDTO(each, this.getStatistical(), this.getPValue());
			dto.getSnps().add(snp);
		}

		return dto;
	}

	@Override
	public AnalysisDTO draft(DraftAnalysisRequestDTO analysis) throws GenotypeDecoderException {
		Analysis entity = this.buildAnalysis(analysis);
		entity.setSnps(this.dtoToSnps(analysis.getSnps()));

		return this.save(entity);
	}

	@Override
	public AnalysisDTO publish(Long analysisId) {
		final Analysis entity = this.findAnalysisById(analysisId);
		entity.setState(AnalysisState.PUBLISHED);

		return this.save(entity);
	}

	private PageRequest gotoPage(int page, int sizePerPage, String sortField, Sort.Direction sortDirection) {
		return PageRequest.of(page, sizePerPage, sortDirection, sortField);
	}


	@Override
	public Page<AnalysisDTO> listDraft(int page, int sizePerPage, String sortField, String sortOrder, String search) {
		Sort.Direction sortDirection = (sortOrder.toLowerCase().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageRequest = this.gotoPage(page, sizePerPage, sortField, sortDirection); // Genero la pagina solicitada
		Page<Analysis> result;

		if (search.equals("")) {
			result = this.getAnalysisRepository().findByState(AnalysisState.DRAFT, pageRequest);
		} else {
			result = this.getAnalysisRepository().findByStateAndDescriptionContainingIgnoreCase(AnalysisState.DRAFT, search, pageRequest);
		}


		return result.map(each -> this.toDTO(each));
	}

	@Override
	public Page<AnalysisDTO> listPublished(int page, int sizePerPage, String sortField, String sortOrder, String search) {
		Sort.Direction sortDirection = (sortOrder.toLowerCase().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageRequest = this.gotoPage(page, sizePerPage, sortField, sortDirection); // Genero la pagina solicitada
		Page<Analysis> result;

		if (search.equals("")) {
			result = this.getAnalysisRepository().findByState(AnalysisState.PUBLISHED, pageRequest);
		} else {
			result = this.getAnalysisRepository().findByStateAndDescriptionContainingIgnoreCase(AnalysisState.PUBLISHED, search, pageRequest);
		}


		return result.map(each -> this.toDTO(each));
	}

	@Override
	public Page<AnalysisDTO> list(int page, int sizePerPage, String sortField, String sortOrder, String search) {
		Sort.Direction sortDirection = (sortOrder.toLowerCase().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageRequest = this.gotoPage(page, sizePerPage, sortField, sortDirection); // Genero la pagina solicitada
		Page<Analysis> result;

		if (search.equals("")) {
			result = this.getAnalysisRepository().findAll(pageRequest);
		} else {
			result = this.getAnalysisRepository().findByDescriptionContainingIgnoreCase(search, pageRequest);
		}

		return result.map(each -> this.toDTO(each));
	}

	private AnalysisDTO save(Analysis analysis) {
		analysis = this.getAnalysisRepository().save(analysis);

		return this.toDTO(analysis);
	}

	private Analysis buildAnalysis(PendingAnalysisRequestDTO analysis) throws GenotypeDecoderException {
		final AnalysisBuilder builder = Analysis.builder();

		 return builder
		 	.addDate(new Date())
			.addState(AnalysisState.PENDING)
			.addPatients(this.findPatients(analysis.getPatientsIds()))
			.addCutoffValue(analysis.getCutoffValue())
			.addDescription(analysis.getDescription())
			.addPhenotype(this.findPhenotype(analysis.getPhenotypeId()))
			.createAnalysis();
	}

	private Analysis buildAnalysis(DraftAnalysisRequestDTO analysis) throws GenotypeDecoderException {
		final AnalysisBuilder builder = Analysis.builder();

		 return builder
		 	.addDate(new Date())
			.addState(AnalysisState.DRAFT)
			.addPatients(this.findPatients(analysis.getPatientsIds()))
			.addCutoffValue(analysis.getCutoffValue())
			.addDescription(analysis.getDescription())
			.addPhenotype(this.findPhenotype(analysis.getPhenotypeId()))
			.createAnalysis();
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

	private Analysis findAnalysisById(Long analysisId) throws EntityNotFoundException {
		final Analysis analysis = this.getAnalysisRepository().findById(analysisId)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Analysis found with Id {0}", analysisId)));

		return analysis;
	}

	private AnalysisDTO toDTO(Analysis entity) {
		AnalysisDTO dto = new AnalysisDTO();
		dto.setId(entity.getId());
		dto.setDate(entity.getDate());
		dto.setState(entity.getState());
		dto.setDescription(entity.getDescription());
		dto.setSnps(this.snpsToDTO(entity.getSnps()));
		dto.setCutoffValue(entity.getCutoffValue());
		dto.setPhenotypeKind(entity.getPhenotype().getKind());
		dto.setPhenotypeId(entity.getPhenotype().getId());
		dto.setAnalysisGroups(this.analysisGroupsToDTO(entity.getAnalysisGroups()));

		return dto;
	}

	private Collection<Snp> dtoToSnps(Collection<SnpDTO> snps) {
		return snps
			.stream()
			.map(each -> new Snp(each.getSnp(), each.getStatistical(), each.getPvalue()))
			.collect(Collectors.toSet());
	}

	private Collection<SnpDTO> snpsToDTO(Collection<Snp> snps) {
		return snps
			.stream()
			.map(each -> new SnpDTO(each.getId(), each.getSnp(), each.getPvalue(), each.getStatistical()))
			.collect(Collectors.toSet());
	}

	private Collection<AnalysisGroupDTO> analysisGroupsToDTO(Collection<AnalysisGroup> analysisGroups) {
		return analysisGroups
			.stream()
			.map(each -> new AnalysisGroupDTO(each.getPhenotype(), this.patientsToDTO(each.getPatients())))
			.collect(Collectors.toList());
	}

	private Collection<PatientDTO> patientsToDTO(Collection<Patient> patients) {
		return this.patientsTransformer.manyToDto(patients);
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

	public GenotypeDecoderService getGenotypeDecoderService() {
		return this.genotypeDecoderService;
	}

	public void setGenotypeDecoderService(GenotypeDecoderService genotypeDecoderService) {
		this.genotypeDecoderService = genotypeDecoderService;
	}

	@Override
	public void delete(Long analysisId) throws EntityNotFoundException {
		final Analysis analysis = this.findAnalysisById(analysisId);

		this.getAnalysisRepository().delete(analysis);
	}
}
