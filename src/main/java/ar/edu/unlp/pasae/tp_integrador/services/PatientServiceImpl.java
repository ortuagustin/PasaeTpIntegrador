package ar.edu.unlp.pasae.tp_integrador.services;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeValueDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.GenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeValueDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotypeValue;
import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;
import ar.edu.unlp.pasae.tp_integrador.entities.Genotype;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotypeValue;
import ar.edu.unlp.pasae.tp_integrador.entities.Patient;
import ar.edu.unlp.pasae.tp_integrador.entities.Patient.PatientBuilder;
import ar.edu.unlp.pasae.tp_integrador.repositories.CategoricPhenotypeRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.CustomUserRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.NumericPhenotypeRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.PatientRepository;
import ar.edu.unlp.pasae.tp_integrador.transformers.Transformer;

@Service
@SuppressWarnings("unused")
public class PatientServiceImpl implements PatientService {
	@Autowired
	private PatientRepository patientRepository;
	@Autowired
	private CustomUserRepository userRepository;
	@Autowired
	private CategoricPhenotypeRepository categoricPhenotypesRepository;
	@Autowired
	private NumericPhenotypeRepository numericPhenotypeRepository;
	@Autowired
	private GenotypeDecoderService genotypeDecoderService;

	@Autowired
	private Transformer<Patient, PatientDTO> transformer;
	@Autowired
	private Transformer<Genotype, GenotypeDTO> genotypeTransformer;

	@Override
	public PatientDTO find(Long patientId) throws EntityNotFoundException {
		final Patient patient = this.findPatientById(patientId);

		return this.toDto(patient);
	}

	@Override
	public PatientDTO findByNameAndSurname(String name, String surname) throws EntityNotFoundException {
		final Patient patient = this.getPatientRepository().findByNameAndSurname(name, surname)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Patient found with name {0} and surname {1}", name, surname)));

		return this.toDto(patient);
	}

	@Override
	public PatientDTO findByDNI(String dni) throws EntityNotFoundException {
		final Patient patient = this.getPatientRepository().findByDni(dni)
				.orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("No Patient found with DNI {0}", dni)));

		return this.toDto(patient);
	}

	@Override
	public Stream<PatientDTO> list() {
		return this.getPatientRepository().findAll().stream().map(each -> this.getTransformer().toDTO(each));
	}

	@Override
	public PatientDTO update(Long patientId, PatientRequestDTO patient) {
		Patient entity = this.buildPatient(patient);
		entity.setId(patientId);
		entity.setUser(this.findRegistrantUser(patientId));

		return this.save(entity);
	}

	@Override
	public PatientDTO create(PatientRequestDTO patient) {
		Patient entity = this.buildPatient(patient);

		return this.save(entity);
	}

	@Override
	public void delete(Long patientId) throws EntityNotFoundException {
		final Patient patient = this.findPatientById(patientId);

		this.getPatientRepository().delete(patient);
	}

	@Override
	public Integer count() {
		return (int) this.getPatientRepository().count();
	}

	private PageRequest gotoPage(int page, int sizePerPage, String sortField, Sort.Direction sortDirection) {
		return PageRequest.of(page, sizePerPage, sortDirection, sortField);
	}

	@Override
	public Page<PatientDTO> list(int page, int sizePerPage, String sortField, String sortOrder, String search) {
		Sort.Direction sortDirection = (sortOrder.toLowerCase().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageRequest = this.gotoPage(page, sizePerPage, sortField, sortDirection); // Genero la pagina solicitada
		Page<Patient> result;

		if (search.equals("")) {
			result = this.getPatientRepository().findAll(pageRequest);
		} else {
			result = this.getPatientRepository()
					.findByNameContainsIgnoreCaseOrSurnameContainsIgnoreCaseOrDniContainsIgnoreCaseOrEmailContainsIgnoreCase(
							search, pageRequest);
		}

		return result.map(each -> this.getTransformer().toDTO(each));
	}

	/**
	 * Devuelve el genotipo de un paciente
	 *
	 * @param patient el paciente
	 */
	private Collection<GenotypeDTO> getPatientGenotype(Patient patient) {
		return this.getGenotypeTransformer().manyToDto(patient.getGenotypes());
	}

	private Patient findPatientById(Long patientId) throws EntityNotFoundException {
		final Patient patient = this.getPatientRepository().findById(patientId).orElseThrow(
				() -> new EntityNotFoundException(MessageFormat.format("No Patient found with Id {0}", patientId)));

		return patient;
	}

	private PatientDTO save(Patient patient) {
		patient = this.getPatientRepository().save(patient);

		return this.toDto(patient);
	}

	private Patient buildPatient(PatientRequestDTO patient) {
		final PatientBuilder builder = Patient.builder();
		final Collection<Genotype> genotypes = this.getGenotypeDecoderService().decodeGenotype(patient.getGenotype());

		final Patient entity = builder
			.addName(patient.getName())
			.addSurname(patient.getSurname())
			.addEmail(patient.getEmail())
			.addDni(patient.getDni())
			.addUser(this.findUser(patient.getUserId()))
			.addCategoricPhenotypes(this.findCategoricPhenotypes(patient.getCategoricPhenotypes()))
			.addNumericPhenotypes(this.findNumericPhenotypes(patient.getNumericPhenotypes()))
			.addGenotypes(genotypes)
			.createPatient();

			return entity;
	}

	private CustomUser findRegistrantUser(Long patientId) {
		final Patient patient = this.findPatientById(patientId);

		return patient.getUser();
	}

	private CustomUser findUser(Long userId) {
		return this.getUserRepository().findById(userId)
				.orElseThrow(() -> new EntityNotFoundException(MessageFormat.format("No User found with id {0}", userId)));
	}

	private Set<CategoricPhenotypeValue> findCategoricPhenotypes(Set<CategoricPhenotypeValueDTO> phenotypes) {
		Set<CategoricPhenotypeValue> entities = new HashSet<CategoricPhenotypeValue>();

		for (CategoricPhenotypeValueDTO each : phenotypes) {
			final CategoricPhenotype phenotype = this.getCategoricPhenotypesRepository().findById(each.getPhenotypeId())
					.orElseThrow(() -> new EntityNotFoundException(
							MessageFormat.format("No Categoric Phenotype found with id {0}", each.getPhenotypeId())));

			entities.add(new CategoricPhenotypeValue(phenotype, each.getValueId()));
		}

		return entities;
	}

	private Set<NumericPhenotypeValue> findNumericPhenotypes(Set<NumericPhenotypeValueDTO> phenotypes) {
		Set<NumericPhenotypeValue> entities = new HashSet<NumericPhenotypeValue>();

		for (NumericPhenotypeValueDTO each : phenotypes) {
			final NumericPhenotype phenotype = this.getNumericPhenotypeRepository().findById(each.getPhenotypeId())
					.orElseThrow(() -> new EntityNotFoundException(
							MessageFormat.format("No Numeric Phenotype found with id {0}", each.getPhenotypeId())));

			entities.add(new NumericPhenotypeValue(phenotype, each.getValue()));
		}

		return entities;
	}

	private PatientDTO toDto(Patient entity) {
		PatientDTO dto = this.getTransformer().toDTO(entity);
		dto.setGenotype(this.getPatientGenotype(entity));

		return dto;
	}

	private CustomUserRepository getUserRepository() {
		return userRepository;
	}

	private void setUserRepository(CustomUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	private PatientRepository getPatientRepository() {
		return patientRepository;
	}

	private void setPatientRepository(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	public NumericPhenotypeRepository getNumericPhenotypeRepository() {
		return numericPhenotypeRepository;
	}

	public void setNumericPhenotypeRepository(NumericPhenotypeRepository numericPhenotypeRepository) {
		this.numericPhenotypeRepository = numericPhenotypeRepository;
	}

	public CategoricPhenotypeRepository getCategoricPhenotypesRepository() {
		return categoricPhenotypesRepository;
	}

	public void setCategoricPhenotypesRepository(CategoricPhenotypeRepository categoricPhenotypesRepository) {
		this.categoricPhenotypesRepository = categoricPhenotypesRepository;
	}

	private Transformer<Patient, PatientDTO> getTransformer() {
		return transformer;
	}

	private void setTransformer(Transformer<Patient, PatientDTO> transformer) {
		this.transformer = transformer;
	}

	public Transformer<Genotype, GenotypeDTO> getGenotypeTransformer() {
		return genotypeTransformer;
	}

	public void setGenotypeTransformer(Transformer<Genotype, GenotypeDTO> genotypeTransformer) {
		this.genotypeTransformer = genotypeTransformer;
	}

	public GenotypeDecoderService getGenotypeDecoderService() {
		return this.genotypeDecoderService;
	}

	public void setGenotypeDecoderService(GenotypeDecoderService genotypeDecoderService) {
		this.genotypeDecoderService = genotypeDecoderService;
	}
}
