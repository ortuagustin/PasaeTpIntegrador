package ar.edu.unlp.pasae.tp_integrador.dtos;

public class CategoricPhenotypeValueDTO {
	private Long phenotypeId;
	private Long valueId;

	@SuppressWarnings("unused")
	private CategoricPhenotypeValueDTO() {
		super();
	}

	public CategoricPhenotypeValueDTO(Long phenotypeId, Long valueId) {
		super();
		this.setPhenotypeId(phenotypeId);
		this.setValue(valueId);
	}

	/**
	 * @return the valueId
	 */
	public Long getValue() {
		return valueId;
	}

	/**
	 * @param valueId the valueId to set
	 */
	public void setValue(Long valueId) {
		this.valueId = valueId;
	}

	/**
	 * @return the phenotypeId
	 */
	public Long getPhenotypeId() {
		return phenotypeId;
	}

	/**
	 * @param phenotypeId the phenotypeId to set
	 */
	public void setPhenotypeId(Long phenotypeId) {
		this.phenotypeId = phenotypeId;
	}
}

