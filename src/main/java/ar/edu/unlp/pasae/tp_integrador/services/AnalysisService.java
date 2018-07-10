package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.stream.Stream;

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisDTO;

public interface AnalysisService {
	AnalysisDTO find(Long id);
	Stream<AnalysisDTO> listDraft();
	Stream<AnalysisDTO> listPublished();
	AnalysisDTO create(AnalysisDTO analysisDTO);
	AnalysisDTO publish(AnalysisDTO analysisDTO);
}
