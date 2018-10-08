package ar.edu.unlp.pasae.tp_integrador.dtos;

public class CategoricPhenotypeValueRequestDTO {
	private Long phenotypeId;
	private Long valueId;

	@SuppressWarnings("unused")
	private CategoricPhenotypeValueRequestDTO() {
		super();
	}

	public CategoricPhenotypeValueRequestDTO(Long phenotypeId, Long valueId) {
		super();
		this.setPhenotypeId(phenotypeId);
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
