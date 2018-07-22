package ar.edu.unlp.pasae.tp_integrador.entities;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Entity
public class CategoricPhenotype extends Phenotype {
  @ElementCollection
  @NotEmpty
  private Set<String> values = new HashSet<String>();

  public static final class CategoricPhenotypeBuilder {
		private String name;
		private Set<String> values = new HashSet<String>();

		private CategoricPhenotypeBuilder() {
		}

		public CategoricPhenotypeBuilder addValues(final Set<String> values) {
			this.values = values;
			return this;
		}

		public CategoricPhenotypeBuilder addName(final String name) {
			this.name = name;
			return this;
		}

		public CategoricPhenotype createPhenotype() {
      final CategoricPhenotype phenotype = new CategoricPhenotype(this.name, this.values);

			return phenotype;
		}
	}

	public static final CategoricPhenotypeBuilder builder() {
		return new CategoricPhenotypeBuilder();
	}

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