package ar.edu.unlp.pasae.tp_integrador.dtos;

import java.util.Date;
import java.util.Set;

import ar.edu.unlp.pasae.tp_integrador.entities.AnalysisState;

public class AnalysisDTO {
  private Long id;
  private Date date;
  private AnalysisState state;
  private String phenotypeKind;
  private Long phenotypeId;
  private Set<Long> patients;
  private Set<String> snps;
  private Long cutoffValue;

  public AnalysisDTO() {
    super();
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
   * @return the patients
   */
  public Set<Long> getPatients() {
    return patients;
  }

  /**
   * @param patients the patients to set
   */
  public void setPatients(Set<Long> patients) {
    this.patients = patients;
  }

  /**
   * @return the id
   */
  public Long getId() {
    return id;
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
   * @return the date
   */
  public Date getDate() {
    return date;
  }

  /**
   * @param date the date to set
   */
  public void setDate(Date date) {
    this.date = date;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
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
}