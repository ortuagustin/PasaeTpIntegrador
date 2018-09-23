package ar.edu.unlp.pasae.tp_integrador.dtos;

public class CategoricPhenotypeValueDTO {
	private Long phenotypeId;
	private Long valueId;
	private String value;
	private String name;

	@SuppressWarnings("unused")
	private CategoricPhenotypeValueDTO() {
		super();
	}

	public CategoricPhenotypeValueDTO(Long phenotypeId, String name, Long valueId) {
		super();
		this.setName(name);
		this.setPhenotypeId(phenotypeId);
		this.setValueId(valueId);
	}

	public CategoricPhenotypeValueDTO(Long phenotypeId, String name, Long valueId, String value) {
		super();
		this.setName(name);
		this.setPhenotypeId(phenotypeId);
		this.setValueId(valueId);
		this.setValue(value);
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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
}
