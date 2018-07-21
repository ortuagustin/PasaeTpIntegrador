package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import ar.edu.unlp.pasae.tp_integrador.dtos.GenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientRequestDTO;

public interface PatientService {
	/**
	 * Devuelve un paciente dado su id
	 *
	 * @param patientId el id del paciente
	 *
	 * @return dto con los datos del paciente bsucado
	 */
	PatientDTO find(Long patientId);

	/**
	 * Devuelve un paciente dado su dni
	 *
	 * @param dni el dni del paciente
	 *
	 * @return dto con los datos del paciente bsucado
	 */
	PatientDTO findByDNI(String dni) throws EntityNotFoundException;

	/**
	 * Devuelve un paciente dado su nombre y apellido
	 *
	 * @param name    el nombre del paciente
	 * @param surname el apellido del paciente
	 *
	 * @return dto con los datos del paciente bsucado
	 */
	PatientDTO findByNameAndSurname(String name, String surname) throws EntityNotFoundException;

	/**
	 * Devuelve un Stream con todos los pacientes del sistema
	 *
	 * @return stream de pacientes
	 */
	Stream<PatientDTO> list();

	/**
	 * Crea un paciente
	 *
	 * @param patient dto con los datos del paciente
	 *
	 * @return dto con los datos del paciente persistido
	 */
	PatientDTO create(PatientRequestDTO patient);

	/**
	 * Devuelve la cantidad de pacientes
	 *
	 * @return la cantidad de pacientes
	 */
	Integer count();

	/**
	 * Actualiza un paciente
	 *
	 * @param patient dto con los datos a actualizar
	 *
	 * @return dto con los datos del paciente actualizado
	 */
	PatientDTO update(PatientRequestDTO patient);

	/**
	 * Actualiza el genotipo de un paciente
	 *
	 * @param patientId el id del paciente
	 * @param genotypes lista de objetos genotipo
	 */
	void setPatientGenotype(Long patientId, List<GenotypeDTO> genotypes);

	/**
	 * Elimina un paciente
	 *
	 * @param patientId el id del paciente
	 */
	void delete(Long patientId);
}
