package ar.edu.unlp.pasae.tp_integrador.services;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.GenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Genotype;
import ar.edu.unlp.pasae.tp_integrador.entities.Patient;
import javax.persistence.EntityNotFoundException;
import ar.edu.unlp.pasae.tp_integrador.repositories.PatientRepository;
import ar.edu.unlp.pasae.tp_integrador.transformers.Transformer;

@Service
public class PatientServiceImpl implements PatientService {
	@Autowired
	private PatientRepository repository;
	@Autowired
	private Transformer<Patient, PatientDTO> transformer;
	@Autowired
	private Transformer<Genotype, GenotypeDTO> genotypeTransformer;

	@Override
	public PatientDTO find(Long patientId) {
		return this.getTransformer().toDTO(this.getRepository().findById(patientId).get());
	}

	@Override
	public PatientDTO findByNameAndSurname(String name, String surname) throws EntityNotFoundException {
		final Patient patient = this.getRepository().findByNameAndSurname(name, surname)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Patient found with name {0} and surname {1}", name, surname)));

		return this.getTransformer().toDTO(patient);
	}

	@Override
	public PatientDTO findByDNI(String dni) throws EntityNotFoundException {
		final Patient patient = this.getRepository().findByDni(dni)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Patient found with DNI {0}", dni)));

		return this.getTransformer().toDTO(patient);
	}

	@Override
	public Stream<PatientDTO> list() {
		return this.getRepository().findAll().stream().map(each -> this.getTransformer().toDTO(each));
	}

	@Override
	public PatientDTO update(PatientDTO patient) {
		Patient entity = this.getRepository().save(this.getTransformer().toEntity(patient));

		return this.getTransformer().toDTO(entity);
	}

	@Override
	public PatientDTO create(PatientDTO patientDTO) {
		Patient patient = this.getRepository().save(this.getTransformer().toEntity(patientDTO));

		return this.getTransformer().toDTO(patient);
	}

	@Override
	public void delete(Long patientId) {
		this.getRepository().deleteById(patientId);
	}

	@Override
	public Integer count() {
		return (int) this.getRepository().count();
	}

	@Override
	public void setPatientGenotype(Long patientId, List<GenotypeDTO> genotypes) {
		final Patient patient = this.getRepository().getOne(patientId);
		patient.setGenotypes(this.getGenotypeTransformer().manyToEntity(genotypes));
		this.getRepository().save(patient);
	}

	private PatientRepository getRepository() {
		return repository;
	}

	private void setRepository(PatientRepository repository) {
		this.repository = repository;
	}

	private Transformer<Patient, PatientDTO> getTransformer() {
		return transformer;
	}

	private void setTransformer(Transformer<Patient, PatientDTO> transformer) {
		this.transformer = transformer;
	}

	/**
	 * @return the genotypeTransformer
	 */
	public Transformer<Genotype, GenotypeDTO> getGenotypeTransformer() {
		return genotypeTransformer;
	}

	/**
	 * @param genotypeTransformer the genotypeTransformer to set
	 */
	public void setGenotypeTransformer(Transformer<Genotype, GenotypeDTO> genotypeTransformer) {
		this.genotypeTransformer = genotypeTransformer;
	}
}
