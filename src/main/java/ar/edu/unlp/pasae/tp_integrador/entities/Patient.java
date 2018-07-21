package ar.edu.unlp.pasae.tp_integrador.entities;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Entity
public class Patient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String name;
	@NotEmpty
	private String surname;
	@Pattern(regexp = "\\d{7,8}")
	private String dni;
	@Email
	private String email;
	@OneToOne
	private CustomUser user;
	@OneToMany
	private List<Genotype> genotypes = new ArrayList<Genotype>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<NumericPhenotype> numericPhenotypes = new HashSet<NumericPhenotype>();
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<CategoricPhenotype> categoricPhenotypes = new HashSet<CategoricPhenotype>();

	public static final class PatientBuilder {
		private String name;
		private String surname;
		private String dni;
		private String email;
		private CustomUser user;
		private List<Genotype> genotypes = new ArrayList<Genotype>();
		private Set<NumericPhenotype> numericPhenotypes = new HashSet<NumericPhenotype>();
		private Set<CategoricPhenotype> categoricPhenotypes = new HashSet<CategoricPhenotype>();

		private PatientBuilder() {
		}

		public PatientBuilder addNumericPhenotypes(final Set<NumericPhenotype> numericPhenotypes) {
			this.numericPhenotypes = numericPhenotypes;
			return this;
		}

		public PatientBuilder addCategoricPhenotypes(final Set<CategoricPhenotype> categoricPhenotypes) {
			this.categoricPhenotypes = categoricPhenotypes;
			return this;
		}

		public PatientBuilder addGenotypes(final List<Genotype> genotypes) {
			this.genotypes = genotypes;
			return this;
		}

		public PatientBuilder addName(final String name) {
			this.name = name;
			return this;
		}

		public PatientBuilder addUser(final CustomUser user) {
			this.user = user;
			return this;
		}

		public PatientBuilder addSurname(final String surname) {
			this.surname = surname;
			return this;
		}

		public PatientBuilder addDni(final String dni) {
			this.dni = dni;
			return this;
		}

		public PatientBuilder addEmail(final String email) {
			this.email = email;
			return this;
		}

		public Patient createPatient() {
			final Patient patient = new Patient(this.name, this.surname, this.dni, this.user);
			patient.setNumericPhenotypes(this.numericPhenotypes);
			patient.setCategoricPhenotypes(this.categoricPhenotypes);
			patient.setGenotypes(this.genotypes);
			patient.setEmail(this.email);

			return patient;
		}
	}

	public static final PatientBuilder builder() {
		return new PatientBuilder();
	}

	public Patient() {
		super();
	}

	public Patient(String name, String surname, String dni, CustomUser user) {
		super();
		this.setName(name);
		this.setSurname(surname);
		this.setDni(dni);
		this.setEmail(email);
		this.setUser(user);
	}

	/**
	 * Adds the genotype to the collection
	 *
	 * @param genotype the genotype to add
	 *
	 * @return this
	 */
	public Patient addGenotype(Genotype genotype) {
		this.getGenotypes().add(genotype);

		return this;
	}

	/**
	 * Adds the phenotype to the collection
	 *
	 * @param phenotype the Phenotype to add
	 *
	 * @return this
	 */
	public Patient addPhenotype(NumericPhenotype phenotype) {
		this.getNumericPhenotypes().add(phenotype);

		return this;
	}

	/**
	 * Adds the phenotype to the collection
	 *
	 * @param phenotype the Phenotype to add
	 *
	 * @return this
	 */
	public Patient addPhenotype(CategoricPhenotype phenotype) {
		this.getCategoricPhenotypes().add(phenotype);

		return this;
	}

	/**
	 * @return the genotype
	 */
	public Collection<Genotype> getGenotypes() {
		return genotypes;
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
	 * @return the user
	 */
	public CustomUser getUser() {
		return user;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param genotypes the genotype list to set
	 */
	public void setGenotypes(Collection<Genotype> genotypes) {
		this.genotypes.clear();
		this.genotypes.addAll(genotypes);
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
	public Set<CategoricPhenotype> getCategoricPhenotypes() {
		return categoricPhenotypes;
	}

	/**
	 * @param categoricPhenotypes the categoricPhenotypes to set
	 */
	public void setCategoricPhenotypes(Set<CategoricPhenotype> categoricPhenotypes) {
		this.categoricPhenotypes = categoricPhenotypes;
	}

	/**
	 * @return the numericPhenotypes
	 */
	public Set<NumericPhenotype> getNumericPhenotypes() {
		return numericPhenotypes;
	}

	/**
	 * @param numericPhenotypes the numericPhenotypes to set
	 */
	public void setNumericPhenotypes(Set<NumericPhenotype> numericPhenotypes) {
		this.numericPhenotypes = numericPhenotypes;
	}
}