package ar.edu.unlp.pasae.tp_integrador.transformers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.entities.Pathology;
import ar.edu.unlp.pasae.tp_integrador.entities.Pathology.PathologyBuilder;
import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyDTO;

@Service
public class PathologyTransformer extends BaseTransformer<Pathology, PathologyDTO> {
	@Autowired
	private PhenotypeTransformer phenotypeTransformer;

	@Override
	public Pathology toEntity(PathologyDTO dto) {
		final PathologyBuilder builder = Pathology.builder();

		 return builder
		 	.addName(dto.getName())
			.createPathology();
	}

	@Override
	public PathologyDTO toDTO(Pathology entity) {
		Set<NumericPhenotypeDTO> numericPhenotypes = this.getPhenotypeTransformer().numericToDtos(entity.getNumericPhenotypes());
		Set<CategoricPhenotypeDTO> categoricPhenotypes = this.getPhenotypeTransformer().categoricToDtos(entity.getCategoricPhenotypes());

		return new PathologyDTO(entity.getId(), entity.getName(), numericPhenotypes, categoricPhenotypes);
	}

	/**
	 * @return the phenotypeTransformer
	 */
	public PhenotypeTransformer getPhenotypeTransformer() {
		return phenotypeTransformer;
	}

	/**
	 * @param phenotypeTransformer the phenotypeTransformer to set
	 */
	public void setPhenotypeTransformer(PhenotypeTransformer phenotypeTransformer) {
		this.phenotypeTransformer = phenotypeTransformer;
	}
}
