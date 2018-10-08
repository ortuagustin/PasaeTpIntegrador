package ar.edu.unlp.pasae.tp_integrador.dtos;

public class NumericPhenotypeValueRequestDTO {
	private Long id;
	private Long value;

	@SuppressWarnings("unused")
	private NumericPhenotypeValueRequestDTO() {
		super();
	}

	public NumericPhenotypeValueRequestDTO(Long id, Long value) {
		super();
		this.setPhenotypeId(id);
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
