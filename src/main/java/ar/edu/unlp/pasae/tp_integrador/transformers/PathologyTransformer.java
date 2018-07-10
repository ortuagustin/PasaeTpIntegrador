package ar.edu.unlp.pasae.tp_integrador.transformers;

import java.util.HashSet;
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
	private Transformer<NumericPhenotype, NumericPhenotypeDTO> numericPhenotypeTransformer;
	@Autowired
	private Transformer<CategoricPhenotype, CategoricPhenotypeDTO> categoricPhenotypeTransformer;

	@Override
	public Pathology toEntity(PathologyDTO dto) {
		Set<NumericPhenotype> numericPhenotypes = this.numericToEntities(dto.getNumericPhenotypes());
		Set<CategoricPhenotype> categoricPhenotypes = this.categoricToEntities(dto.getCategoricPhenotypes());

		return new Pathology(dto.getId(), dto.getName(), numericPhenotypes, categoricPhenotypes);
	}

	@Override
	public PathologyDTO toDTO(Pathology entity) {
		Set<NumericPhenotypeDTO> numericPhenotypes = this.numericToDtos(entity.getNumericPhenotypes());
		Set<CategoricPhenotypeDTO> categoricPhenotypes = this.categoricToDtos(entity.getCategoricPhenotypes());

		return new PathologyDTO(entity.getId(), entity.getName(), numericPhenotypes, categoricPhenotypes);
	}

	private Set<NumericPhenotypeDTO> numericToDtos(Set<NumericPhenotype> phenotypes) {
		Set<NumericPhenotypeDTO> dtos = new HashSet<NumericPhenotypeDTO>();

		for (NumericPhenotype each : phenotypes) {
			dtos.add(this.getNumericPhenotypeTransformer().toDTO(each));
		}

		return dtos;
	}

	private Set<CategoricPhenotypeDTO> categoricToDtos(Set<CategoricPhenotype> phenotypes) {
		Set<CategoricPhenotypeDTO> dtos = new HashSet<CategoricPhenotypeDTO>();

		for (CategoricPhenotype each : phenotypes) {
			dtos.add(this.getCategoricPhenotypeTransformer().toDTO(each));
		}

		return dtos;
	}

	private Set<CategoricPhenotype> categoricToEntities(Set<CategoricPhenotypeDTO> phenotypes) {
		Set<CategoricPhenotype> entities = new HashSet<CategoricPhenotype>();

		for (CategoricPhenotypeDTO each : phenotypes) {
			entities.add(this.getCategoricPhenotypeTransformer().toEntity(each));
		}

		return entities;
	}

	private Set<NumericPhenotype> numericToEntities(Set<NumericPhenotypeDTO> phenotypes) {
		Set<NumericPhenotype> entities = new HashSet<NumericPhenotype>();

		for (NumericPhenotypeDTO each : phenotypes) {
			entities.add(this.getNumericPhenotypeTransformer().toEntity(each));
		}

		return entities;
	}

	/**
	 * @return the categoricPhenotypeTransformer
	 */
	public Transformer<CategoricPhenotype, CategoricPhenotypeDTO> getCategoricPhenotypeTransformer() {
		return categoricPhenotypeTransformer;
	}

	/**
	 * @param categoricPhenotypeTransformer the categoricPhenotypeTransformer to set
	 */
	public void setCategoricPhenotypeTransformer(Transformer<CategoricPhenotype, CategoricPhenotypeDTO> categoricPhenotypeTransformer) {
		this.categoricPhenotypeTransformer = categoricPhenotypeTransformer;
	}

	/**
	 * @return the numericPhenotypeTransformer
	 */
	public Transformer<NumericPhenotype, NumericPhenotypeDTO> getNumericPhenotypeTransformer() {
		return numericPhenotypeTransformer;
	}

	/**
	 * @param numericPhenotypeTransformer the numericPhenotypeTransformer to set
	 */
	public void setNumericPhenotypeTransformer(Transformer<NumericPhenotype, NumericPhenotypeDTO> numericPhenotypeTransformer) {
		this.numericPhenotypeTransformer = numericPhenotypeTransformer;
	}
}
