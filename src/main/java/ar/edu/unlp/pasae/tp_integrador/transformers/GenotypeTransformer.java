package ar.edu.unlp.pasae.tp_integrador.transformers;

import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.entities.Genotype;
import ar.edu.unlp.pasae.tp_integrador.dtos.GenotypeDTO;

@Service
public class GenotypeTransformer extends BaseTransformer<Genotype, GenotypeDTO> {
	@Override
	public Genotype toEntity(GenotypeDTO dto) {
		return new Genotype(dto.getId(), dto.getSnp(), dto.getValue());
	}

	@Override
	public GenotypeDTO toDTO(Genotype entity) {
		return new GenotypeDTO(entity.getId(), entity.getSnp(), entity.getValue());
	}
}
