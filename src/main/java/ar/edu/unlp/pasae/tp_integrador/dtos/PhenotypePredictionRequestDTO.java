package ar.edu.unlp.pasae.tp_integrador.dtos;

import java.util.ArrayList;
import java.util.Collection;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public class PhenotypePredictionRequestDTO {
	@NotNull
	private Long analysisId;
	@NotEmpty
	private Collection<GenotypeDTO> snps = new ArrayList<>();

	public PhenotypePredictionRequestDTO() {
		super();
	}

	public PhenotypePredictionRequestDTO(Long analysisId, Collection<GenotypeDTO> snps) {
		super();
		this.setAnalysisId(analysisId);
		this.setSnps(snps);
	}

	/**
	 * @return the Snps
	 */
	public Collection<GenotypeDTO> getSnps() {
		return snps;
	}

	/**
	 * @param snps the Snps to set
	 */
	public void setSnps(Collection<GenotypeDTO> snps) {
		this.snps = snps;
	}

	/**
	 * @return the analysisId
	 */
	public Long getAnalysisId() {
		return analysisId;
	}

	/**
	 * @param analysisId the analysisId to set
	 */
	public void setAnalysisId(Long analysisId) {
		this.analysisId = analysisId;
	}
}