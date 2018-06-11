package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import ar.edu.unlp.pasae.tp_integrador.dtos.PatientDTO;

public interface PatientService {
	PatientDTO find(Long id);
	PatientDTO findByNameAndSurname(String name, String surname) throws EntityNotFoundException;
	PatientDTO findByDNI(String dni) throws EntityNotFoundException;
	Stream<PatientDTO> list();
	PatientDTO create(PatientDTO Patient);
	Integer count();
	PatientDTO update(PatientDTO Patient);
	void delete(Long id);
}
