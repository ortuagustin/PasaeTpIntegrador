package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.entities.Genotype;

@Service
public class GenotypeDecoderServiceImpl implements GenotypeDecoderService {
  public List<Genotype> decodeGenotype(String genotype) {
    List<Genotype> genotypes = new ArrayList<Genotype>();

    Pattern pattern = Pattern.compile("(?:rs)(\\d+)([actg]{2})", Pattern.CASE_INSENSITIVE);
    Matcher matcher = pattern.matcher(genotype);

    while (matcher.find()) {
      // la regex arma 3 grupos:
      // el 0 es la regex completa (porque le puse ?:, es decir, no captura)
      // el 1 es el snp
      // el 2 son los alelos

      Integer snp = Integer.parseInt(matcher.group(1));
      String value = matcher.group(2);

      genotypes.add(new Genotype(snp, value));
    }

    return genotypes;
  }
}