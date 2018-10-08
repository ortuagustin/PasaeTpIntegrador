package ar.edu.unlp.pasae.tp_integrador.transformers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeValueDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeValueDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotypeValue;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotypeValue;

@Service
public class PhenotypeTransformerImpl implements PhenotypeTransformer {
  @Autowired
  private Transformer<NumericPhenotype, NumericPhenotypeDTO> numericPhenotypeTransformer;
  @Autowired
  private Transformer<CategoricPhenotype, CategoricPhenotypeDTO> categoricPhenotypeTransformer;

  @Override
  public Set<NumericPhenotypeValueDTO> numericValuedToDtos(Set<NumericPhenotypeValue> phenotypes) {
    Set<NumericPhenotypeValueDTO> dtos = new HashSet<NumericPhenotypeValueDTO>();

    for (NumericPhenotypeValue each : phenotypes) {
      dtos.add(new NumericPhenotypeValueDTO(this.numericToDto(each.getPhenotype()), each.getValue()));
    }

    return dtos;
  }

  @Override
  public Set<CategoricPhenotypeValueDTO> categoricValuedToDtos(Set<CategoricPhenotypeValue> phenotypes) {
    Set<CategoricPhenotypeValueDTO> dtos = new HashSet<CategoricPhenotypeValueDTO>();

    for (CategoricPhenotypeValue each : phenotypes) {
      dtos.add(new CategoricPhenotypeValueDTO(this.categoricToDto(each.getPhenotype()), each.getValueId()));
    }

    return dtos;
  }

  @Override
  public Set<NumericPhenotypeDTO> numericToDtos(Set<NumericPhenotype> phenotypes) {
    Set<NumericPhenotypeDTO> dtos = new HashSet<NumericPhenotypeDTO>();

    for (NumericPhenotype each : phenotypes) {
      dtos.add(this.numericToDto(each));
    }

    return dtos;
  }

  @Override
  public Set<CategoricPhenotypeDTO> categoricToDtos(Set<CategoricPhenotype> phenotypes) {
    Set<CategoricPhenotypeDTO> dtos = new HashSet<CategoricPhenotypeDTO>();

    for (CategoricPhenotype each : phenotypes) {
      dtos.add(this.categoricToDto(each));
    }

    return dtos;
  }

  @Override
  public Set<CategoricPhenotype> categoricToEntities(Set<CategoricPhenotypeDTO> phenotypes) {
    Set<CategoricPhenotype> entities = new HashSet<CategoricPhenotype>();

    for (CategoricPhenotypeDTO each : phenotypes) {
      entities.add(this.getCategoricPhenotypeTransformer().toEntity(each));
    }

    return entities;
  }

  @Override
  public Set<NumericPhenotype> numericToEntities(Set<NumericPhenotypeDTO> phenotypes) {
    Set<NumericPhenotype> entities = new HashSet<NumericPhenotype>();

    for (NumericPhenotypeDTO each : phenotypes) {
      entities.add(this.getNumericPhenotypeTransformer().toEntity(each));
    }

    return entities;
  }

  /**
   * @return the categoricPhenotypeTransformer
   *
   * @Override
   */
  public Transformer<CategoricPhenotype, CategoricPhenotypeDTO> getCategoricPhenotypeTransformer() {
    return categoricPhenotypeTransformer;
  }

  /**
   * @param categoricPhenotypeTransformer the categoricPhenotypeTransformer to set
   *
   * @Override
   */
  public void setCategoricPhenotypeTransformer(Transformer<CategoricPhenotype, CategoricPhenotypeDTO> categoricPhenotypeTransformer) {
    this.categoricPhenotypeTransformer = categoricPhenotypeTransformer;
  }

  /**
   * @return the numericPhenotypeTransformer
   *
   * @Override
   */
  public Transformer<NumericPhenotype, NumericPhenotypeDTO> getNumericPhenotypeTransformer() {
    return numericPhenotypeTransformer;
  }

  /**
   * @param numericPhenotypeTransformer the numericPhenotypeTransformer to set
   *
   * @Override
   */
  public void setNumericPhenotypeTransformer(
      Transformer<NumericPhenotype, NumericPhenotypeDTO> numericPhenotypeTransformer) {
    this.numericPhenotypeTransformer = numericPhenotypeTransformer;
  }

  private NumericPhenotypeDTO numericToDto(NumericPhenotype entity) {
    return this.getNumericPhenotypeTransformer().toDTO(entity);
  }

  private CategoricPhenotypeDTO categoricToDto(CategoricPhenotype entity) {
    return this.getCategoricPhenotypeTransformer().toDTO(entity);
  }
}
