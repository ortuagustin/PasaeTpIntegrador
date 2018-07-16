package ar.edu.unlp.pasae.tp_integrador.transformers;

import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype;

@Service
public class CategoricPhenotypeTransformer extends BaseTransformer<CategoricPhenotype, CategoricPhenotypeDTO> {
	@Override
	public CategoricPhenotype toEntity(CategoricPhenotypeDTO dto) {
		return new CategoricPhenotype(dto.getId(), dto.getName(), dto.getValues());
	}

	@Override
	public CategoricPhenotypeDTO toDTO(CategoricPhenotype entity) {
		return new CategoricPhenotypeDTO(entity.getId(), entity.getName(), entity.getValues());
	}
}