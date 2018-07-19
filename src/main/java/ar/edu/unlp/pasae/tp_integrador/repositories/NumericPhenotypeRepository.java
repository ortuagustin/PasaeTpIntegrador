package ar.edu.unlp.pasae.tp_integrador.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;

public interface NumericPhenotypeRepository extends JpaRepository<NumericPhenotype, Long> {
  Optional<NumericPhenotype> findByName(String name);
}
