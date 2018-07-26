package ar.edu.unlp.pasae.tp_integrador.services;

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
	 * @return dto con los datos del paciente solicitado
	 */
	PatientDTO find(Long patientId) throws EntityNotFoundException;

	/**
	 * Devuelve un paciente dado su dni
	 *
	 * @param dni el dni del paciente
	 *
	 * @return dto con los datos del paciente solicitado
	 */
	PatientDTO findByDNI(String dni) throws EntityNotFoundException;

	/**
	 * Devuelve un paciente dado su nombre y apellido
	 *
	 * @param name    el nombre del paciente
	 * @param surname el apellido del paciente
	 *
	 * @return dto con los datos del paciente solicitado
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
	 * @param patientId id del paciente a actualizar
	 * @param patient dto con los datos a actualizar
	 *
	 * @return dto con los datos del paciente actualizado
	 */
	PatientDTO update(Long patientId, PatientRequestDTO patient);

	/**
	 * Actualiza el genotipo de un paciente
	 *
	 * @param patientId el id del paciente
	 * @param genotype genotipo del paciente (en formato rsXXXMF)
	 */
	Stream<GenotypeDTO> setPatientGenotype(Long patientId, String genotype);

	/**
	 * Devuelve el genotipo de un paciente
	 *
	 * @param patientId el id del paciente
	 */
	Stream<GenotypeDTO> getPatientGenotype(Long patientId);

	/**
	 * Elimina un paciente
	 *
	 * @param patientId el id del paciente
	 */
	void delete(Long patientId) throws EntityNotFoundException;
}
