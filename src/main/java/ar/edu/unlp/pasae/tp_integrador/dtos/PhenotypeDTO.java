package ar.edu.unlp.pasae.tp_integrador.dtos;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PhenotypeDTO {
	private Long id;
	@NotEmpty
	private String name;
	@NotNull
	private PhenotypeKindDTO kind;

	public PhenotypeDTO(Long id, String name, PhenotypeKindDTO kind) {
		super();
		this.setId(id);
		this.setName(name);
		this.setKind(kind);
	}

	public PhenotypeDTO(String name, PhenotypeKindDTO kind) {
		super();
		this.setName(name);
		this.setKind(kind);
	}

	private PhenotypeDTO() {
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
	 * @return the kind
	 */
	public PhenotypeKindDTO getKind() {
		return kind;
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

	/**
	 * @param kind the kind to set
	 */
	public void setKind(PhenotypeKindDTO kind) {
		this.kind = kind;
  }
}