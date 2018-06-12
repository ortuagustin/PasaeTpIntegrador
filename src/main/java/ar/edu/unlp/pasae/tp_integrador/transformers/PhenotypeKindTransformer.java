package ar.edu.unlp.pasae.tp_integrador.transformers;

import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.PhenotypeKindDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.PhenotypeKind;

// TODO ver como instanciar uno u otro tipo de fenotipo (numerico o lista de valores)
@Service
public class PhenotypeKindTransformer implements Transformer<PhenotypeKind, PhenotypeKindDTO> {
	@Override
	public PhenotypeKind toEntity(PhenotypeKindDTO dto) {
		return new PhenotypeKind(dto.getId(), dto.getName());
	}

	@Override
	public PhenotypeKindDTO toDTO(PhenotypeKind entity) {
		return new PhenotypeKindDTO(entity.getId(), entity.getName());
	}
}
