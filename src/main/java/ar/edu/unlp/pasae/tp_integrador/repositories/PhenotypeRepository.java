package ar.edu.unlp.pasae.tp_integrador.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unlp.pasae.tp_integrador.entities.Phenotype;

public interface PhenotypeRepository extends JpaRepository<Phenotype, Long> {
  Optional<Phenotype> findByName(String name);
}
