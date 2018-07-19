package ar.edu.unlp.pasae.tp_integrador.dtos;

import java.util.Set;
import java.util.HashSet;

import javax.validation.constraints.NotEmpty;

public class PathologyDTO {
	private Long id;
	@NotEmpty
	private String name;
	private Set<NumericPhenotypeDTO> numericPhenotypes = new HashSet<NumericPhenotypeDTO>();
	private Set<CategoricPhenotypeDTO> categoricPhenotypes = new HashSet<CategoricPhenotypeDTO>();

	public PathologyDTO(Long id, String name, Set<NumericPhenotypeDTO> numericPhenotypes, Set<CategoricPhenotypeDTO> categoricPhenotypes) {
		super();
		this.setId(id);
		this.setName(name);
		this.setCategoricPhenotypes(categoricPhenotypes);
		this.setNumericPhenotypes(numericPhenotypes);
	}

	public PathologyDTO(String name) {
		super();
		this.setName(name);
	}

	private PathologyDTO() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the categoricPhenotypes
	 */
	public Set<CategoricPhenotypeDTO> getCategoricPhenotypes() {
		return categoricPhenotypes;
	}

	/**
	 * @param categoricPhenotypes the categoricPhenotypes to set
	 */
	public void setCategoricPhenotypes(Set<CategoricPhenotypeDTO> categoricPhenotypes) {
		this.categoricPhenotypes = categoricPhenotypes;
	}

	/**
	 * @return the numericPhenotypes
	 */
	public Set<NumericPhenotypeDTO> getNumericPhenotypes() {
		return numericPhenotypes;
	}

	/**
	 * @param numericPhenotypes the numericPhenotypes to set
	 */
	public void setNumericPhenotypes(Set<NumericPhenotypeDTO> numericPhenotypes) {
		this.numericPhenotypes = numericPhenotypes;
	}
}