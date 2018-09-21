package ar.edu.unlp.pasae.tp_integrador.entities;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;

@Entity
@SuppressWarnings("unused")
public class Analysis {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Date date;
	private AnalysisState state;
	@OneToOne
	private Pathology pathology;
	@NotEmpty
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Patient> patients = new HashSet<Patient>();
	@ElementCollection
  @NotEmpty
	private Set<String> snps = new HashSet<String>();
	@OneToOne
	private Phenotype phenotype;
	private Double cutoffValue;

	public static final class AnalysisBuilder {
		private Date date;
		private AnalysisState state;
		private Pathology pathology;
		private Set<Patient> patients = new HashSet<Patient>();
		private Set<String> snps = new HashSet<String>();
		private Phenotype phenotype;
		private Double cutoffValue;

		private AnalysisBuilder() {}

		public AnalysisBuilder addPhenotype(final Phenotype phenotype) {
			this.phenotype = phenotype;
			return this;
		}

		public AnalysisBuilder addCutoffValue(final Double cutoffValue) {
			this.cutoffValue = cutoffValue;
			return this;
		}

		public AnalysisBuilder addPathology(final Pathology pathology) {
			this.pathology = pathology;
			return this;
		}

		public AnalysisBuilder addSnps(final Set<String> snps) {
			this.snps = snps;
			return this;
		}

		public AnalysisBuilder addPatients(final Set<Patient> patients) {
			this.patients = patients;
			return this;
		}

		public AnalysisBuilder addDate(final Date date) {
			this.date = date;
			return this;
		}

		public AnalysisBuilder addState(final AnalysisState state) {
			this.state = state;
			return this;
		}

		public Analysis createAnalysis() {
      final Analysis analysis = new Analysis(this.date, this.state, this.patients, this.pathology, this.snps, this.phenotype, this.cutoffValue);

			return analysis;
		}
	}

	public static final AnalysisBuilder builder() {
		return new AnalysisBuilder();
	}

	public Analysis(Long id, Date date, AnalysisState state, Set<Patient> patients, Pathology pathology, Set<String> snps,
			Phenotype phenotype, Double cutoffValue) {
		this(date, state, patients, pathology, snps, phenotype, cutoffValue);
		this.setId(id);
	}

	public Analysis(Date date, AnalysisState state, Collection<Patient> patients, Pathology pathology,
			Collection<String> snps, Phenotype phenotype, Double cutoffValue) {
		super();
		this.setDate(date);
		this.setState(state);
		this.setPatients(patients);
		this.setPathology(pathology);
		this.setSnps(snps);
		this.setPhenotype(phenotype);
		this.setCutoffValue(cutoffValue);
	}

	private Analysis() {
		super();
		this.setState(AnalysisState.PENDING);
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @return the state
	 */
	public AnalysisState getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(AnalysisState state) {
		this.state = state;
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
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	* @return the snps
	*/
	public Set<String> getSnps() {
		return this.snps;
	}

	/**
	 * @param snps the snps to set
	 */
	public void setSnps(Collection<String> snps) {
		this.snps.clear();
		this.snps.addAll(snps);
	}

	/**
	* @return the patients
	*/
	public Set<Patient> getPatients() {
		return this.patients;
	}

	/**
	 * @param patients the patients to set
	 */
	public void setPatients(Collection<Patient> patients) {
		this.patients.clear();
		this.patients.addAll(patients);
	}

	/**
	* @return the Pathology
	*/
	public Pathology getPathology() {
		return this.pathology;
	}

	/**
	 * @param pathology the pathology to set
	 */
	public void setPathology(Pathology pathology) {
		this.pathology = pathology;
	}

	/**
	* @return the Phenotype
	*/
	public Phenotype getPhenotype() {
		return this.phenotype;
	}

	/**
	 * @param phenotype the phenotype to set
	 */
	public void setPhenotype(Phenotype phenotype) {
		this.phenotype = phenotype;
	}


  /**
   * @return the cutoffValue
   */
  public Double getCutoffValue() {
    return cutoffValue;
  }

  /**
   * @param cutoffValue the cutoffValue to set
   */
  public void setCutoffValue(Double cutoffValue) {
    this.cutoffValue = cutoffValue;
  }
}