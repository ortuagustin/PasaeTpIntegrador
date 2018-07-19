package ar.edu.unlp.pasae.tp_integrador.entities;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;

@Entity
public class Pathology {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String name;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<NumericPhenotype> numericPhenotypes = new HashSet<NumericPhenotype>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CategoricPhenotype> categoricPhenotypes = new HashSet<CategoricPhenotype>();

	public Pathology(Long id, String name, Set<NumericPhenotype> numericPhenotypes, Set<CategoricPhenotype> categoricPhenotypes) {
		super();
		this.setId(id);
		this.setName(name);
		this.setCategoricPhenotypes(categoricPhenotypes);
		this.setNumericPhenotypes(numericPhenotypes);
	}

	public Pathology(String name, Set<NumericPhenotype> numericPhenotypes, Set<CategoricPhenotype> categoricPhenotypes) {
		super();
		this.setName(name);
		this.setCategoricPhenotypes(categoricPhenotypes);
		this.setNumericPhenotypes(numericPhenotypes);
	}

	public Pathology() {
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
	 * Adds the phenotype to the collection
	 *
	 * @param phenotype the Phenotype to add
	 *
	 * @return this
	 */
	public Pathology addPhenotype(NumericPhenotype phenotype) {
		this.getNumericPhenotypes().add(phenotype);

		return this;
	}

	/**
	 * Adds the phenotype to the collection
	 *
	 * @param phenotype the Phenotype to add
	 *
	 * @return this
	 */
	public Pathology addPhenotype(CategoricPhenotype phenotype) {
		this.getCategoricPhenotypes().add(phenotype);

		return this;
	}

	/**
	 * @return the categoricPhenotypes
	 */
	public Set<CategoricPhenotype> getCategoricPhenotypes() {
		return categoricPhenotypes;
	}

	/**
	 * @param categoricPhenotypes the categoricPhenotypes to set
	 */
	public void setCategoricPhenotypes(Set<CategoricPhenotype> categoricPhenotypes) {
		this.categoricPhenotypes = categoricPhenotypes;
	}

	/**
	 * @return the numericPhenotypes
	 */
	public Set<NumericPhenotype> getNumericPhenotypes() {
		return numericPhenotypes;
	}

	/**
	 * @param numericPhenotypes the numericPhenotypes to set
	 */
	public void setNumericPhenotypes(Set<NumericPhenotype> numericPhenotypes) {
		this.numericPhenotypes = numericPhenotypes;
	}
}