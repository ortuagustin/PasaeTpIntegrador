package ar.edu.unlp.pasae.tp_integrador.transformers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.PhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PhenotypeKindDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Phenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.PhenotypeKind;

@Service
public class PhenotypeTransformer implements Transformer<Phenotype, PhenotypeDTO> {
	@Autowired
	private Transformer<PhenotypeKind, PhenotypeKindDTO> kindTransformer;

	@Override
	public Phenotype toEntity(PhenotypeDTO dto) {
		return new Phenotype(dto.getId(), dto.getName(), kindTransformer.toEntity(dto.getKind()));
	}

	@Override
	public PhenotypeDTO toDTO(Phenotype entity) {
		return new PhenotypeDTO(entity.getId(), entity.getName(), kindTransformer.toDTO(entity.getKind()));
	}
}
