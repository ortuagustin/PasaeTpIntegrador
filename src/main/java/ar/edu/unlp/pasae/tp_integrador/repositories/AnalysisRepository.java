package ar.edu.unlp.pasae.tp_integrador.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unlp.pasae.tp_integrador.entities.Analysis;
import ar.edu.unlp.pasae.tp_integrador.entities.AnalysisState;

public interface AnalysisRepository extends JpaRepository<Analysis, Long> {
  List<Analysis> findByState(AnalysisState state);
}
