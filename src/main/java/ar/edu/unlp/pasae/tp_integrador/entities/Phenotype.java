package ar.edu.unlp.pasae.tp_integrador.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

// TODO modelar relacion OneToOne con PhenotypeKind
@Entity
public class Phenotype {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String name;
	@NotNull
	@OneToOne
	private PhenotypeKind kind;

	public Phenotype(Long id, String name, PhenotypeKind kind) {
		super();
		this.setId(id);
		this.setName(name);
		this.setKind(kind);
	}

	public Phenotype(String name, PhenotypeKind kind) {
		super();
		this.setName(name);
		this.setKind(kind);
	}

	public Phenotype() {
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
	public PhenotypeKind getKind() {
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
	public void setKind(PhenotypeKind kind) {
		this.kind = kind;
	}
}