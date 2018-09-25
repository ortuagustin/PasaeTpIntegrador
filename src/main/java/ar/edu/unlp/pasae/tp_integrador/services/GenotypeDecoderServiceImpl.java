package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.entities.Genotype;
import ar.edu.unlp.pasae.tp_integrador.exceptions.GenotypeDecoderException;

@Service
public class GenotypeDecoderServiceImpl implements GenotypeDecoderService {
  private Pattern pattern;

  public GenotypeDecoderServiceImpl() {
    super();
    pattern = Pattern.compile("(?:rs)(\\d+)\\s([actg]{2})$", Pattern.CASE_INSENSITIVE);
  }

  public List<Genotype> decodeGenotype(String genotype) throws GenotypeDecoderException {
    List<Genotype> genotypes = new ArrayList<Genotype>();

    if (genotype.isEmpty()) {
      return genotypes;
    }

    List<GenotypeDecoderError> errors = new ArrayList<GenotypeDecoderError>();

    int index = 0;
    for (String line : genotype.split("\\n")) {

      Matcher matcher = this.pattern.matcher(line);

      if (matcher.find()) {
        // la regex arma 3 grupos:
        // el 0 es la regex completa (porque le puse ?:, es decir, no captura)
        // el 1 es el snp
        // el 2 son los alelos

        Integer snp = Integer.parseInt(matcher.group(1));
        String value = matcher.group(2);

        genotypes.add(new Genotype(snp, value));
      } else {
        errors.add(new GenotypeDecoderError(line, index));
      }

      index++;
    }

    if (!errors.isEmpty()) {
      throw new GenotypeDecoderException("Failed to decode genotype", errors);
    }

    return genotypes;
  }
}