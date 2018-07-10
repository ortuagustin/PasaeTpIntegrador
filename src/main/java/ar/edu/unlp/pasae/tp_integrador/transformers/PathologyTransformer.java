package ar.edu.unlp.pasae.tp_integrador.transformers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.Pathology;
import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyDTO;

@Service
public class PathologyTransformer implements Transformer<Pathology, PathologyDTO> {
	@Autowired
	private PhenotypeTransformer phenotypeTransformer;

	@Override
	public Pathology toEntity(PathologyDTO dto) {
		Set<NumericPhenotype> numericPhenotypes = this.getPhenotypeTransformer().numericToEntities(dto.getNumericPhenotypes());
		Set<CategoricPhenotype> categoricPhenotypes = this.getPhenotypeTransformer().categoricToEntities(dto.getCategoricPhenotypes());

		return new Pathology(dto.getId(), dto.getName(), numericPhenotypes, categoricPhenotypes);
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
