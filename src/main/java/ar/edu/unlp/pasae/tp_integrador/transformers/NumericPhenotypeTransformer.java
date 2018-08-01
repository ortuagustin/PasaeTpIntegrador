package ar.edu.unlp.pasae.tp_integrador.transformers;

import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;

@Service
public class NumericPhenotypeTransformer extends BaseTransformer<NumericPhenotype, NumericPhenotypeDTO> {
	@Override
	public NumericPhenotype toEntity(NumericPhenotypeDTO dto) {
		return new NumericPhenotype(dto.getId(), dto.getName());
	}

	@Override
	public NumericPhenotypeDTO toDTO(NumericPhenotype entity) {
		return new NumericPhenotypeDTO(entity.getId(), entity.getName());
	}
}