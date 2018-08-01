package ar.edu.unlp.pasae.tp_integrador.dtos;

import java.util.Set;
import java.util.HashSet;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * DTO que se usa para altas/modificaciones de pacientes
 */
@SuppressWarnings("unused")
public class PatientRequestDTO {
	private Long userId;
	@NotEmpty
	private String name;
	@NotEmpty
	private String surname;
	@NotEmpty
	private String dni;
	@Email
	private String email;
	private Set<NumericPhenotypeValueDTO> numericPhenotypes = new HashSet<NumericPhenotypeValueDTO>();
	private Set<CategoricPhenotypeValueDTO> categoricPhenotypes = new HashSet<CategoricPhenotypeValueDTO>();

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
	 * @return the categoricPhenotypes
	 */
	public Set<CategoricPhenotypeValueDTO> getCategoricPhenotypes() {
		return categoricPhenotypes;
	}

	/**
	 * @param categoricPhenotypes the categoricPhenotypes to set
	 */
	public void setCategoricPhenotypes(Set<CategoricPhenotypeValueDTO> categoricPhenotypes) {
		this.categoricPhenotypes.clear();
		this.categoricPhenotypes.addAll(categoricPhenotypes);
	}

	/**
	 * @return the numericPhenotypes
	 */
	public Set<NumericPhenotypeValueDTO> getNumericPhenotypes() {
		return numericPhenotypes;
	}

	/**
	 * @param numericPhenotypes the numericPhenotypes to set
	 */
	public void setNumericPhenotypes(Set<NumericPhenotypeValueDTO> numericPhenotypes) {
		this.numericPhenotypes.clear();
		this.numericPhenotypes.addAll(numericPhenotypes);
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
}