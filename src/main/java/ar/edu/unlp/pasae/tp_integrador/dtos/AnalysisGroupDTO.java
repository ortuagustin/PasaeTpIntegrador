package ar.edu.unlp.pasae.tp_integrador.dtos;

import java.util.ArrayList;
import java.util.Collection;

public class AnalysisGroupDTO {
	private String phenotypeValue;
	private Collection<PatientDTO> patients = new ArrayList<>();

	public AnalysisGroupDTO(String phenotypeValue, Collection<PatientDTO> patients) {
		super();
		this.setPhenotypeValue(phenotypeValue);
		this.setPatients(patients);
	}

	/**
	 * @return the patients
	 */
	public Collection<PatientDTO> getPatients() {
		return this.patients;
	}

	/**
	 * @param patients the patients to set
	 */
	public void setPatients(Collection<PatientDTO> patients) {
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