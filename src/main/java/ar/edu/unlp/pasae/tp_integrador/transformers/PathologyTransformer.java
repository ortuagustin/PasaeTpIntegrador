package ar.edu.unlp.pasae.tp_integrador.transformers;

import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.entities.Pathology;
import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyDTO;

@Service
public class PathologyTransformer implements Transformer<Pathology, PathologyDTO> {
	@Override
	public Pathology toEntity(PathologyDTO dto) {
		return new Pathology(dto.getId(), dto.getName());
	}

	@Override
	public PathologyDTO toDTO(Pathology entity) {
		return new PathologyDTO(entity.getId(), entity.getName());
	}
}
