package ar.edu.unlp.pasae.tp_integrador.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype;

public interface CategoricPhenotypeRepository extends JpaRepository<CategoricPhenotype, Long> {
  Optional<CategoricPhenotype> findByName(String name);
  Page<CategoricPhenotype> findByNameContainsIgnoreCase(String search, Pageable pageRequest);
}
