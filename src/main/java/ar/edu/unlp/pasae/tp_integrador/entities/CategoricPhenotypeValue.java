package ar.edu.unlp.pasae.tp_integrador.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class CategoricPhenotypeValue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@OneToOne
	private CategoricPhenotype phenotype;
	private Long valueId;

	@SuppressWarnings("unused")
	private CategoricPhenotypeValue() {
		super();
	}

	public CategoricPhenotypeValue(CategoricPhenotype phenotype, Long valueId) {
		super();
		this.setPhenotype(phenotype);
		this.setValue(valueId);
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return this.getPhenotype().getValues().get(this.getValueId());
	}

	/**
	 * @return the valueId
	 */
	public Long getValueId() {
		return valueId;
	}

	/**
	 * @param valueId the valueId to set
	 */
	public void setValue(Long valueId) {
		this.valueId = valueId;
	}

	/**
	 * @return the phenotype
	 */
	public CategoricPhenotype getPhenotype() {
		return phenotype;
	}

	/**
	 * @param phenotype the phenotype to set
	 */
	public void setPhenotype(CategoricPhenotype phenotype) {
		this.phenotype = phenotype;
	}
}
