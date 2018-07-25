package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.List;

import ar.edu.unlp.pasae.tp_integrador.entities.Genotype;

public interface GenotypeDecoderService {
  List<Genotype> decodeGenotype(String genotype);
}