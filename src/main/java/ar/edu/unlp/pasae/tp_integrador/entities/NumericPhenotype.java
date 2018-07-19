package ar.edu.unlp.pasae.tp_integrador.entities;

import javax.persistence.Entity;

@Entity
public class NumericPhenotype extends Phenotype {
  public NumericPhenotype(Long id, String name) {
    super(id, name);
  }

  public NumericPhenotype(String name) {
    super(name);
  }

  protected NumericPhenotype() {
    super();
  }

  @Override
  public Boolean validate(String value) {
    try {
      Integer.parseInt(value);
      return true;
    } catch (Exception NumberFormatException) {
      return false;
    }
  }
}