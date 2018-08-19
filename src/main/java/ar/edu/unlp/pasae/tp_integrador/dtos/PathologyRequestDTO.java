package ar.edu.unlp.pasae.tp_integrador.dtos;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

/**
 * DTO que se usa para altas/modificaciones de patologias
 */
@SuppressWarnings("unused")
public class PathologyRequestDTO {
	private Long id;
	@NotEmpty
	private String name;
	private Set<Long> numericPhenotypes = new HashSet<Long>();
	private Set<Long> categoricPhenotypes = new HashSet<Long>();

	public PathologyRequestDTO(Long id, String name, Set<Long> numericPhenotypes, Set<Long> categoricPhenotypes) {
		super();
		this.setId(id);
		this.setName(name);
		this.setNumericPhenotypes(numericPhenotypes);
		this.setCategoricPhenotypes(categoricPhenotypes);
	}

	public PathologyRequestDTO(String name, Set<Long> numericPhenotypes, Set<Long> categoricPhenotypes) {
		super();
		this.setName(name);
		this.setNumericPhenotypes(numericPhenotypes);
		this.setCategoricPhenotypes(categoricPhenotypes);
	}

	private PathologyRequestDTO() {
		super();
	}

	/**
	 * @return the categoricPhenotypes
	 */
	public Set<Long> getCategoricPhenotypes() {
		return categoricPhenotypes;
	}

	/**
	 * @param categoricPhenotypes the categoricPhenotypes to set
	 */
	public void setCategoricPhenotypes(Set<Long> categoricPhenotypes) {
		this.categoricPhenotypes.clear();
		this.categoricPhenotypes.addAll(categoricPhenotypes);
	}

	/**
	 * @return the numericPhenotypes
	 */
	public Set<Long> getNumericPhenotypes() {
		return numericPhenotypes;
	}

	/**
	 * @param numericPhenotypes the numericPhenotypes to set
	 */
	public void setNumericPhenotypes(Set<Long> numericPhenotypes) {
		this.numericPhenotypes.clear();
		this.numericPhenotypes.addAll(numericPhenotypes);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}