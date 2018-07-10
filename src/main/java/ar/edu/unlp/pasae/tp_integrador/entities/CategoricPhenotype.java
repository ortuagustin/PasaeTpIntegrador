package ar.edu.unlp.pasae.tp_integrador.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;

@Entity
public class CategoricPhenotype extends Phenotype {
  @ElementCollection
  private Set<String> values = new HashSet<String>();

  public CategoricPhenotype(String name, Set<String> values) {
    super(name);
    this.setValues(values);
  }

  public CategoricPhenotype(Long id, String name, Set<String> values) {
    super(id, name);
    this.setValues(values);
  }

  protected CategoricPhenotype() {
    super();
  }

  /**
   * @return the values
   */
  public Set<String> getValues() {
    return values;
  }

  /**
   * @param values the values to set
   */
  public void setValues(Set<String> values) {
    this.values = values;
  }

  @Override
  public Boolean validate(String value) {
    return this.getValues().contains(value);
  }
}