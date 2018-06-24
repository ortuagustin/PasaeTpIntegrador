package ar.edu.unlp.pasae.tp_integrador.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

// TODO mapear jerarquia (numerico - lista de valores)
@Entity
public class PhenotypeKind {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String name;

	public PhenotypeKind(Long id, String name) {
		super();
		this.setId(id);
		this.setName(name);
	}

	public PhenotypeKind(String name) {
		super();
		this.setName(name);
	}

	public PhenotypeKind() {
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