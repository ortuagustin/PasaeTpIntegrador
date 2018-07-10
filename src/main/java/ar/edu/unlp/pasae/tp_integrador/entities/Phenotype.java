package ar.edu.unlp.pasae.tp_integrador.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;

@MappedSuperclass
public abstract class Phenotype {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotEmpty
  private String name;

  public Phenotype(Long id, String name) {
    super();
    this.setId(id);
    this.setName(name);
  }

  public Phenotype(String name) {
    super();
    this.setId(id);
    this.setName(name);
  }

  protected Phenotype() {
    super();
  }

  /**
   * Checks if the value to assign is valid in the Phenotype
   *
   * @param vale the value to set
   *
   * @return True if the validation passes; False otherwise
   */
  public abstract Boolean validate(String value);

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }
}