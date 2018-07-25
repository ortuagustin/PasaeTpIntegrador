package ar.edu.unlp.pasae.tp_integrador.dtos;

import javax.validation.constraints.NotEmpty;

@SuppressWarnings("unused")
public class GenotypeDTO {
  private Long id;
  /**
   * posicion en el genoma para este "valor"
   */
  @NotEmpty
  private Integer snp;

  /**
   * El valor del genotipo en si (A, D, T, G)
   */
  @NotEmpty
  private String value;

  public GenotypeDTO(Long id, Integer snp, String value) {
    super();
    this.setId(id);
    this.setSnp(snp);
    this.setValue(value);
  }

  public GenotypeDTO(Integer snp, String value) {
    super();
    this.setSnp(snp);
    this.setValue(value);
  }

  private GenotypeDTO() {
    super();
  }

  /**
   * @return el valor del alelo heredado del padre
   */
  public String getFatherValue() {
    Character c = this.getValue().charAt(0);

    return c.toString();
  }

  /**
   * @return el valor del alelo heredado de la madre
   */
  public String getMotherValue() {
    Character c = this.getValue().charAt(1);

    return c.toString();
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
   * @return the value
   */
  public String getValue() {
    return value;
  }

  /**
   * @param value the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * @return the snp
   */
  public Integer getSnp() {
    return snp;
  }

  /**
   * @param snp the snp to set
   */
  public void setSnp(Integer snp) {
    this.snp = snp;
  }
}