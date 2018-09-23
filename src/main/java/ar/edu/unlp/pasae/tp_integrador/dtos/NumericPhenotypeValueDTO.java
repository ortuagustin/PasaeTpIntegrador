package ar.edu.unlp.pasae.tp_integrador.dtos;

public class NumericPhenotypeValueDTO {
	private Long phenotypeId;
	private Long value;
	private String name;

	@SuppressWarnings("unused")
	private NumericPhenotypeValueDTO() {
		super();
	}

	public NumericPhenotypeValueDTO(Long phenotypeId, String name, Long value) {
		super();
		this.setName(name);
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
