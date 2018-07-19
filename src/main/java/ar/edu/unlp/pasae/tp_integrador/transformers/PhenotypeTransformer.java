package ar.edu.unlp.pasae.tp_integrador.transformers;

import java.util.Set;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;

public interface PhenotypeTransformer {
    Set<NumericPhenotypeDTO> numericToDtos(Set<NumericPhenotype> phenotypes);

    Set<CategoricPhenotypeDTO> categoricToDtos(Set<CategoricPhenotype> phenotypes);

    Set<CategoricPhenotype> categoricToEntities(Set<CategoricPhenotypeDTO> phenotypes);

    Set<NumericPhenotype> numericToEntities(Set<NumericPhenotypeDTO> phenotypes);
}
