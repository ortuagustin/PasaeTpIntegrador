package ar.edu.unlp.pasae.tp_integrador.dtos;

import java.util.Set;
import java.util.HashSet;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;

/**
 * DTO que se usa para devolver el objeto paciente
 */
@SuppressWarnings("unused")
public class PatientDTO {
	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String surname;
	@NotEmpty
	private String dni;
	@Email
	private String email;
	@NotNull
	private CustomUser user;
	private Set<NumericPhenotypeDTO> numericPhenotypes = new HashSet<NumericPhenotypeDTO>();
	private Set<CategoricPhenotypeDTO> categoricPhenotypes = new HashSet<CategoricPhenotypeDTO>();

	public PatientDTO(Long id, String name, String surname, String dni, String email, CustomUser user) {
		super();
		this.setId(id);
		this.setName(name);
		this.setSurname(surname);
		this.setDni(dni);
		this.setEmail(email);
		this.setUser(user);
	}

	public PatientDTO(String name, String surname, String dni, String email, CustomUser user) {
		super();
		this.setName(name);
		this.setSurname(surname);
		this.setDni(dni);
		this.setEmail(email);
		this.setUser(user);
	}

	private PatientDTO() {
		super();
	}

	/**
	 * @return the user
	 */
	public CustomUser getUser() {
		return user;
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
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
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
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * @param dni the dni to set
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(CustomUser user) {
		this.user = user;
	}

	/**
	 * @return the categoricPhenotypes
	 */
	public Set<CategoricPhenotypeDTO> getCategoricPhenotypes() {
		return categoricPhenotypes;
	}

	/**
	 * @param categoricPhenotypes the categoricPhenotypes to set
	 */
	public void setCategoricPhenotypes(Set<CategoricPhenotypeDTO> categoricPhenotypes) {
		this.categoricPhenotypes = categoricPhenotypes;
	}

	/**
	 * @return the numericPhenotypes
	 */
	public Set<NumericPhenotypeDTO> getNumericPhenotypes() {
		return numericPhenotypes;
	}

	/**
	 * @param numericPhenotypes the numericPhenotypes to set
	 */
	public void setNumericPhenotypes(Set<NumericPhenotypeDTO> numericPhenotypes) {
		this.numericPhenotypes = numericPhenotypes;
	}
}