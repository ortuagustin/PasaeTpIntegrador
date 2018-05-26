package ar.edu.unlp.pasae.tp_integrador.services;

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.PatientDTO;
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

	@Override
	public PatientDTO find(Long id) {
		return this.getTransformer().toDTO(this.getRepository().findById(id).get());
	}

	@Override
	public PatientDTO findByNameAndSurname(String name, String surname) throws EntityNotFoundException {
		final Patient Patient = this.getRepository().findByNameAndSurname(name, surname)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Patient found with name {0} and surname {1}", name, surname)));

		return this.getTransformer().toDTO(Patient);
	}

	@Override
	public PatientDTO findByDNI(String dni) throws EntityNotFoundException {
		final Patient Patient = this.getRepository().findByDni(dni)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Patient found with DNI {0}", dni)));

		return this.getTransformer().toDTO(Patient);
	}

	@Override
	public Stream<PatientDTO> list() {
		return this.getRepository().findAll().stream().map(each -> this.getTransformer().toDTO(each));
	}

	@Override
	public void update(PatientDTO Patient) {
		this.getRepository().save(this.getTransformer().toEntity(Patient));
	}

	@Override
	public PatientDTO create(PatientDTO PatientDTO) {
		Patient Patient = this.getRepository().save(this.getTransformer().toEntity(PatientDTO));

		return this.getTransformer().toDTO(Patient);
	}

	@Override
	public void delete(Long id) {
		this.getRepository().deleteById(id);
	}

	@Override
	public Integer count() {
		return (int) this.getRepository().count();
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
}
