package ar.edu.unlp.pasae.tp_integrador.dtos;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

public class AnalysisRequestDTO {
  private Long pathologyId;
  @NotEmpty
  private Set<Long> patientsIds;
  @NotEmpty
  private String phenotypeKind;
  private Long phenotypeId;
  @NotEmpty
  private Set<String> snps;
  private Double cutoffValue;

  public AnalysisRequestDTO(Long pathologyId, Set<Long> patientsIds, String phenotypeKind, Long phenotypeId, Set<String> snps) {
    super();
    this.setPathologyId(pathologyId);
    this.setPatientsIds(patientsIds);
    this.setPhenotypeKind(phenotypeKind);
    this.setPhenotypeId(phenotypeId);
    this.setSnps(snps);
    this.setCutoffValue(null);
  }

  public AnalysisRequestDTO(Long pathologyId, Set<Long> patientsIds, String phenotypeKind, Long phenotypeId, Set<String> snps, Double cutoffValue) {
    this(pathologyId, patientsIds, phenotypeKind, phenotypeId, snps);
    this.setCutoffValue(cutoffValue);
  }

  public AnalysisRequestDTO() {
    super();
  }

  /**
   * @return the snps
   */
  public Set<String> getSnps() {
    return snps;
  }

  /**
   * @param snps the snps to set
   */
  public void setSnps(Set<String> snps) {
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
   * @return the pathologyId
   */
  public Long getPathologyId() {
    return pathologyId;
  }

  /**
   * @param pathologyId the pathologyId to set
   */
  public void setPathologyId(Long pathologyId) {
    this.pathologyId = pathologyId;
  }

  /**
   * @return the cutoffValue
   */
  public Double getcutoffValue() {
    return cutoffValue;
  }

  /**
   * @param cutoffValue the cutoffValue to set
   */
  public void setCutoffValue(Double cutoffValue) {
    this.cutoffValue = cutoffValue;
  }
}