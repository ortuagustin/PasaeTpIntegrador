package ar.edu.unlp.pasae.tp_integrador.dtos;

public class PhenotypePredictionResultDTO {
  private Long phenotypeId;
  private String phenotypeKind;
  private String phenotypeValue;

  public PhenotypePredictionResultDTO() {
    super();
  }

  public PhenotypePredictionResultDTO(Long phenotypeId, String phenotypeKind, String phenotypeValue) {
    super();
    this.setPhenotypeId(phenotypeId);
    this.setPhenotypeKind(phenotypeKind);
    this.setPhenotypeValue(phenotypeValue);
  }

  /**
   * @return the phenotypeId
   */
  public Long getPhenotypeId() {
    return phenotypeId;
  }

  /**
   * @return the phenotypeValue
   */
  public String getPhenotypeValue() {
    return phenotypeValue;
  }

  /**
   * @param phenotypeValue the phenotypeValue to set
   */
  public void setPhenotypeValue(String phenotypeValue) {
    this.phenotypeValue = phenotypeValue;
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
   * @param phenotypeId the phenotypeId to set
   */
  public void setPhenotypeId(Long phenotypeId) {
    this.phenotypeId = phenotypeId;
  }
}