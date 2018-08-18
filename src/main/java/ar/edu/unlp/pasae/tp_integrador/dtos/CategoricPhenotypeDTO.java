package ar.edu.unlp.pasae.tp_integrador.dtos;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("unused")
public class CategoricPhenotypeDTO {
	private Long id;
	@NotEmpty
	private String name;
	@NotNull
	@Size(min = 2)
	private Set<String> values = new HashSet<String>();

	public CategoricPhenotypeDTO(Long id, String name, Set<String> values) {
		super();
		this.setId(id);
		this.setName(name);
		this.setValues(values);
	}

	public CategoricPhenotypeDTO(String name, Set<String> values) {
		super();
		this.setName(name);
		this.setValues(values);
	}

	private CategoricPhenotypeDTO() {
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
	 * @return the values
	 */
	public Set<String> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(Set<String> values) {
		this.values.clear();
		this.values.addAll(values);
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