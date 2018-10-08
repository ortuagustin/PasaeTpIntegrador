package ar.edu.unlp.pasae.tp_integrador.dtos;

public class CategoricPhenotypeValueRequestDTO {
	private Long id;
	private Long valueId;

	@SuppressWarnings("unused")
	private CategoricPhenotypeValueRequestDTO() {
		super();
	}

	public CategoricPhenotypeValueRequestDTO(Long id, Long valueId) {
		super();
		this.setId(id);
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
