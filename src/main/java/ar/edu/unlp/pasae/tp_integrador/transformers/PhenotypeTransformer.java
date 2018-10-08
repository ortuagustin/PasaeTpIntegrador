package ar.edu.unlp.pasae.tp_integrador.transformers;

import java.util.Set;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeValueDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeValueDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotypeValue;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotypeValue;

public interface PhenotypeTransformer {
    Set<NumericPhenotypeDTO> numericToDtos(Set<NumericPhenotype> phenotypes);

    Set<CategoricPhenotypeDTO> categoricToDtos(Set<CategoricPhenotype> phenotypes);

    Set<NumericPhenotypeValueDTO> numericValuedToDtos(Set<NumericPhenotypeValue> phenotypes);

    Set<CategoricPhenotypeValueDTO> categoricValuedToDtos(Set<CategoricPhenotypeValue> phenotypes);

    Set<CategoricPhenotype> categoricToEntities(Set<CategoricPhenotypeDTO> phenotypes);

    Set<NumericPhenotype> numericToEntities(Set<NumericPhenotypeDTO> phenotypes);
}
