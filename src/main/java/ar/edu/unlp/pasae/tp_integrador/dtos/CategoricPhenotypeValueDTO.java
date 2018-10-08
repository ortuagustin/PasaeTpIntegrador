package ar.edu.unlp.pasae.tp_integrador.dtos;

public class CategoricPhenotypeValueDTO {
	private CategoricPhenotypeDTO phenotype;
	private Long valueId;

	@SuppressWarnings("unused")
	private CategoricPhenotypeValueDTO() {
		super();
	}

	public CategoricPhenotypeValueDTO(CategoricPhenotypeDTO phenotype, Long valueId) {
		super();
		this.setPhenotype(phenotype);
		this.setValueId(valueId);
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
	public void setValueId(Long valueId) {
		this.valueId = valueId;
	}

	/**
	 * @return the phenotype
	 */
	public CategoricPhenotypeDTO getPhenotype() {
		return phenotype;
	}

	/**
	 * @param phenotype the phenotype to set
	 */
	public void setPhenotype(CategoricPhenotypeDTO phenotype) {
		this.phenotype = phenotype;
	}
}
