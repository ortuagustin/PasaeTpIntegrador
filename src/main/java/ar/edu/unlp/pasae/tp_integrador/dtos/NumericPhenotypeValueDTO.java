package ar.edu.unlp.pasae.tp_integrador.dtos;

public class NumericPhenotypeValueDTO {
    private Long phenotypeId;
    private Long value;

    public NumericPhenotypeValueDTO(Long phenotypeId, Long value) {
        super();
        this.setPhenotypeId(phenotypeId);
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

