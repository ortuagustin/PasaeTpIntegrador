package ar.edu.unlp.pasae.tp_integrador.dtos;

import java.util.Set;
import java.util.HashSet;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * DTO que se usa para altas/modificaciones de pacientes
 */
public class PatientRequestDTO {
	private Long id;
	private Long userId;
	@NotEmpty
	private String name;
	@NotEmpty
	private String surname;
	@NotEmpty
	private String dni;
	@Email
	private String email;
	private Set<Long> phenotypes = new HashSet<Long>();
	private Set<Long> numericPhenotypes = new HashSet<Long>();
	private Set<Long> categoricPhenotypes = new HashSet<Long>();

	public PatientRequestDTO(Long id, Long userId, String name, String surname, String dni, String email) {
		super();
		this.setId(id);
		this.setUserId(userId);
		this.setName(name);
		this.setSurname(surname);
		this.setDni(dni);
		this.setEmail(email);
	}

	public PatientRequestDTO(Long userId, String name, String surname, String dni, String email) {
		super();
		this.setName(name);
		this.setUserId(userId);
		this.setSurname(surname);
		this.setDni(dni);
		this.setEmail(email);
	}

	private PatientRequestDTO() {
		super();
	}

	/**
	 * @return the phenotypes
	 */
	public Set<Long> getPhenotypes() {
		return phenotypes;
	}

	/**
	 * @param phenotypes the phenotypes to set
	 */
	public void setPhenotypes(Set<Long> phenotypes) {
		this.phenotypes = phenotypes;
	}

	/**
	 * @return the categoricPhenotypes
	 */
	public Set<Long> getCategoricPhenotypes() {
		return categoricPhenotypes;
	}

	/**
	 * @param categoricPhenotypes the categoricPhenotypes to set
	 */
	public void setCategoricPhenotypes(Set<Long> categoricPhenotypes) {
		this.categoricPhenotypes = categoricPhenotypes;
	}

	/**
	 * @return the numericPhenotypes
	 */
	public Set<Long> getNumericPhenotypes() {
		return numericPhenotypes;
	}

	/**
	 * @param numericPhenotypes the numericPhenotypes to set
	 */
	public void setNumericPhenotypes(Set<Long> numericPhenotypes) {
		this.numericPhenotypes = numericPhenotypes;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the dni
	 */
	public String getDni() {
		return dni;
	}

	/**
	 * @param dni the dni to set
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
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

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
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