package ar.edu.unlp.pasae.tp_integrador.dtos;

import javax.validation.constraints.NotEmpty;

public class PathologyDTO {
	private Long id;
	@NotEmpty
	private String name;

	public PathologyDTO(Long id, String name) {
		super();
		this.setId(id);
		this.setName(name);
	}

	public PathologyDTO(String name) {
		super();
		this.setName(name);
	}

	private PathologyDTO() {
		super();
  }

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
  }

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
  }
}