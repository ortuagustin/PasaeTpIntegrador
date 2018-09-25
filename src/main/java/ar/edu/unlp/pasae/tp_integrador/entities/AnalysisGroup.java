package ar.edu.unlp.pasae.tp_integrador.entities;

import java.util.ArrayList;
import java.util.Collection;

public class AnalysisGroup {
	private String phenotypeValue;
	private Collection<Patient> patients = new ArrayList<>();

	public static final class AnalysisGroupBuilder {
		private String phenotypeValue;
		private Collection<Patient> patients = new ArrayList<>();

		private AnalysisGroupBuilder() {}

		public AnalysisGroupBuilder addPhenotypeValue(final String phenotypeValue) {
			this.phenotypeValue = phenotypeValue;
			return this;
		}

		public AnalysisGroupBuilder addPatients(final Collection<Patient> patients) {
			this.patients = patients;
			return this;
		}

		public AnalysisGroup createAnalysisGroup() {
			final AnalysisGroup group = new AnalysisGroup(this.phenotypeValue, this.patients);

			return group;
		}
	}

	public static final AnalysisGroupBuilder builder() {
		return new AnalysisGroupBuilder();
	}

	public AnalysisGroup(String phenotypeValue, Collection<Patient> patients) {
		super();
		this.setPhenotypeValue(phenotypeValue);
		this.setPatients(patients);
	}

	/**
	 * @return the patients
	 */
	public Collection<Patient> getPatients() {
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
	 * @return the phenotypeValue
	 */
	public String getPhenotype() {
		return this.phenotypeValue;
	}

	/**
	 * @param phenotypeValue the phenotypeValue to set
	 */
	public void setPhenotypeValue(String phenotypeValue) {
		this.phenotypeValue = phenotypeValue;
	}
}