package ar.edu.unlp.pasae.tp_integrador.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unlp.pasae.tp_integrador.entities.Patient;

public interface PatientRepository extends JpaRepository<Patient, Long> {
  Optional<Patient> findByDni(String dni);
  Optional<Patient> findByNameAndSurname(String name, String surname);
  Page<Patient> findByNameContainingIgnoreCaseOrSurnameContainingIgnoreCaseOrDniContainingIgnoreCaseOrEmailContainingIgnoreCase(
		String name, String surname, String dni, String email, Pageable pageRequest);
}
