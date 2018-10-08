package ar.edu.unlp.pasae.tp_integrador.dtos;

public class NumericPhenotypeValueDTO {
	private NumericPhenotypeDTO phenotype;
	private Long value;

	@SuppressWarnings("unused")
	private NumericPhenotypeValueDTO() {
		super();
	}

	public NumericPhenotypeValueDTO(NumericPhenotypeDTO phenotype, Long value) {
		super();
		this.setPhenotype(phenotype);
		this.setValue(value);
	}

	/**
	 * @return the value
	 */
	 public Long getValue() {
		 return value;
	 }

	 /**
	  * @param value the value to set
	  */
	 public void setValue(Long value) {
		 this.value = value;
	 }

	/**
	 * @return the phenotype
	 */
	public NumericPhenotypeDTO getPhenotype() {
		return phenotype;
	}

	/**
	 * @param phenotype the phenotype to set
	 */
	public void setPhenotype(NumericPhenotypeDTO phenotype) {
		this.phenotype = phenotype;
	}
}
