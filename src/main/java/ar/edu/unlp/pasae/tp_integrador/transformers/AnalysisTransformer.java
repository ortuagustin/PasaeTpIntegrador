package ar.edu.unlp.pasae.tp_integrador.transformers;

import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Analysis;

@Service
public class AnalysisTransformer extends BaseTransformer<Analysis, AnalysisDTO> {
	@Override
	public Analysis toEntity(AnalysisDTO dto) {
		return new Analysis(dto.getId(), dto.getDate(), dto.getState());
	}

	@Override
	public AnalysisDTO toDTO(Analysis entity) {
		return new AnalysisDTO(entity.getId(), entity.getDate(), entity.getState());
	}
}