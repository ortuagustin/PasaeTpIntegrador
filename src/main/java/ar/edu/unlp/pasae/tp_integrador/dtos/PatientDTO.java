package ar.edu.unlp.pasae.tp_integrador.dtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
	@Pattern(regexp = "\\d{7,8}")
	private String dni;
	@Email
	private String email;
	@NotNull
	private CustomUserDTO user;
	private Collection<GenotypeDTO> genotype = new ArrayList<GenotypeDTO>();
	private Set<NumericPhenotypeValueDTO> numericPhenotypes = new HashSet<NumericPhenotypeValueDTO>();
	private Set<CategoricPhenotypeValueRequestDTO> categoricPhenotypes = new HashSet<CategoricPhenotypeValueRequestDTO>();

	public PatientDTO(String name, String surname, String dni, String email, CustomUserDTO user) {
		super();
		this.setName(name);
		this.setSurname(surname);
		this.setDni(dni);
		this.setEmail(email);
		this.setUser(user);
	}

	public PatientDTO(Long id, String name, String surname, String dni, String email, CustomUserDTO user) {
		this(name, surname, dni, email, user);
		this.setId(id);
	}

	private PatientDTO() {
		super();
	}

	/**
	 * @return the user
	 */
	public CustomUserDTO getUser() {
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
	 * @param genotype the id to set
	 */
	public void setGenotype(Collection<GenotypeDTO> genotype) {
		this.genotype.clear();
		this.genotype.addAll(genotype);
	}

	/**
	 * @return the genotype
	 */
	public Collection<GenotypeDTO> getGenotype() {
		return genotype;
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
	public void setUser(CustomUserDTO user) {
		this.user = user;
	}

	/**
	 * @return the categoricPhenotypes
	 */
	public Set<CategoricPhenotypeValueRequestDTO> getCategoricPhenotypes() {
		return categoricPhenotypes;
	}

	/**
	 * @param categoricPhenotypes the categoricPhenotypes to set
	 */
	public void setCategoricPhenotypes(Set<CategoricPhenotypeValueRequestDTO> categoricPhenotypes) {
		this.categoricPhenotypes = categoricPhenotypes;
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
		this.numericPhenotypes = numericPhenotypes;
	}
}