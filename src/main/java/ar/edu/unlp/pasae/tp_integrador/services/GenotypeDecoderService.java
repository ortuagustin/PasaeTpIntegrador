package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.List;

import ar.edu.unlp.pasae.tp_integrador.entities.Genotype;
import ar.edu.unlp.pasae.tp_integrador.exceptions.GenotypeDecoderException;

public interface GenotypeDecoderService {
  /**
   * Decodifica el genotipo siguiendo el formato "rsXXXXXYZ", donde:
   *  rs es fijo
   *  XXXX es un numero (cualquier cantidad de digitos) y representa el snp
   *  Y es el alelo paterno
   *  Z es el alelo materno
   *
   * Se debe separar cada rs por una nueva linea, es decir un "\n"
   */
  List<Genotype> decodeGenotype(String genotype) throws GenotypeDecoderException;
}