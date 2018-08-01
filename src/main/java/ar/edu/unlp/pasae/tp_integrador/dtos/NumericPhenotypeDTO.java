package ar.edu.unlp.pasae.tp_integrador.dtos;

import javax.validation.constraints.NotEmpty;

@SuppressWarnings("unused")
public class NumericPhenotypeDTO {
	private Long id;
	@NotEmpty
	private String name;

	public NumericPhenotypeDTO(Long id, String name) {
		super();
		this.setId(id);
		this.setName(name);
	}

	public NumericPhenotypeDTO(String name) {
		super();
		this.setName(name);
	}

	private NumericPhenotypeDTO() {
		super();
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