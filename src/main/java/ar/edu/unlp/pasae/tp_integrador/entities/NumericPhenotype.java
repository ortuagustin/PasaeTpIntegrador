package ar.edu.unlp.pasae.tp_integrador.entities;

import javax.persistence.Entity;

@Entity
public class NumericPhenotype extends Phenotype {
  public static final class NumericPhenotypeBuilder {
		private String name;

		private NumericPhenotypeBuilder() {
		}

		public NumericPhenotypeBuilder addName(final String name) {
			this.name = name;
			return this;
		}

		public NumericPhenotype createPhenotype() {
      final NumericPhenotype phenotype = new NumericPhenotype(this.name);

			return phenotype;
		}
	}

	public static final NumericPhenotypeBuilder builder() {
		return new NumericPhenotypeBuilder();
	}

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
      Float.parseFloat(value);
      return true;
    } catch (Exception NumberFormatException) {
      return false;
    }
  }
}