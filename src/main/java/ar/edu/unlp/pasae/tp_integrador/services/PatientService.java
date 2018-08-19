package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;

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
	 * Devuelve un Paginado de pacientes, de acuerdo a los parametros dados
	 * @param page el numero de pagina
	 * @param sizePerPage cantidad de elementos por pagina
	 * @param sortField el campo por el que se desea ordenar
	 * @param sortOrder ascendente o descendente
	 * @param search filtro
	 * @return pagina de paotologias
	 */
	Page<PatientDTO> list(int page, int sizePerPage, String sortField, String sortOrder, String search);

	/**
	 * Elimina un paciente
	 *
	 * @param patientId el id del paciente
	 */
	void delete(Long patientId) throws EntityNotFoundException;
}
