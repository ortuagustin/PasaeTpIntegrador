package ar.edu.unlp.pasae.tp_integrador.dtos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

@SuppressWarnings("unused")
public class DraftAnalysisRequestDTO {
	@NotEmpty
	private Set<Long> patientsIds;
	@NotEmpty
	private String description;
	@NotEmpty
	private String phenotypeKind;
	private Long phenotypeId;
	private Collection<SnpDTO> snps = new ArrayList<>();
	private Long cutoffValue;

	public DraftAnalysisRequestDTO(String description, Set<Long> patientsIds, String phenotypeKind, Long phenotypeId, Collection<SnpDTO> snps) {
		super();
		this.setDescription(description);
		this.setPatientsIds(patientsIds);
		this.setPhenotypeKind(phenotypeKind);
		this.setPhenotypeId(phenotypeId);
		this.setSnps(snps);
		this.setCutoffValue(null);
	}

	public DraftAnalysisRequestDTO(String description, Set<Long> patientsIds, String phenotypeKind, Long phenotypeId, Collection<SnpDTO> snps, Long cutoffValue) {
		this(description, patientsIds, phenotypeKind, phenotypeId, snps);
		this.setCutoffValue(cutoffValue);
	}

	public DraftAnalysisRequestDTO() {
		super();
	}

	/**
	 * @return the snps
	 */
	public Collection<SnpDTO> getSnps() {
		return snps;
	}

	/**
	 * @param snps the snps to set
	 */
	public void setSnps(Collection<SnpDTO> snps) {
		this.snps = snps;
	}

	/**
	 * @return the phenotypeKind
	 */
	public String getPhenotypeKind() {
		return phenotypeKind;
	}

	/**
	 * @param phenotypeKind the phenotypeKind to set
	 */
	public void setPhenotypeKind(String phenotypeKind) {
		this.phenotypeKind = phenotypeKind;
	}

	/**
	 * @return the phenotypeId
	 */
	public Long getPhenotypeId() {
		return phenotypeId;
	}

	/**
	 * @param phenotypeId the phenotypeId to set
	 */
	public void setPhenotypeId(Long phenotypeId) {
		this.phenotypeId = phenotypeId;
	}

	/**
	 * @return the patientsIds
	 */
	public Set<Long> getPatientsIds() {
		return patientsIds;
	}

	/**
	 * @param patientsIds the patientsIds to set
	 */
	public void setPatientsIds(Set<Long> patientsIds) {
		this.patientsIds = patientsIds;
	}

	/**
	 * @return the cutoffValue
	 */
	public Long getCutoffValue() {
		return cutoffValue;
	}

	/**
	 * @param cutoffValue the cutoffValue to set
	 */
	public void setCutoffValue(Long cutoffValue) {
		this.cutoffValue = cutoffValue;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}