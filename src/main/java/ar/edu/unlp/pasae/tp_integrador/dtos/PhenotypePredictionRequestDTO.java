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
	private Collection<GenotypeDTO> genotypes = new ArrayList<>();

	public PhenotypePredictionRequestDTO() {
		super();
	}

	public PhenotypePredictionRequestDTO(Long analysisId, Collection<GenotypeDTO> genotypes) {
		super();
		this.setAnalysisId(analysisId);
		this.setGenotypes(genotypes);
	}

	/**
	 * @return the genotypes
	 */
	public Collection<GenotypeDTO> getGenotypes() {
		return genotypes;
	}

	/**
	 * @param genotypes the genotypes to set
	 */
	public void setGenotypes(Collection<GenotypeDTO> genotypes) {
		this.genotypes = genotypes;
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