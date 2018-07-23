package ar.edu.unlp.pasae.tp_integrador.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;

public interface NumericPhenotypeRepository extends PagingAndSortingRepository<NumericPhenotype, Long> {
  Optional<NumericPhenotype> findByName(String name);
  Page<NumericPhenotype> findByNameContains(String search, Pageable pageRequest);
}
