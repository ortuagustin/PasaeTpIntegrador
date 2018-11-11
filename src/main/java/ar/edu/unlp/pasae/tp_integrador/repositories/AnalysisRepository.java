package ar.edu.unlp.pasae.tp_integrador.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unlp.pasae.tp_integrador.entities.Analysis;
import ar.edu.unlp.pasae.tp_integrador.entities.AnalysisState;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
  Page<Analysis> findByState(AnalysisState state, Pageable pageRequest);
  Page<Analysis> findByDescriptionContainingIgnoreCase(String search, Pageable pageRequest);
  Page<Analysis> findByStateAndDescriptionContainingIgnoreCase(AnalysisState state, String search, Pageable pageRequest);
}
