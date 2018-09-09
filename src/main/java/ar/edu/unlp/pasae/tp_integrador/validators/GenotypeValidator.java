package ar.edu.unlp.pasae.tp_integrador.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.constraints.GenotypeConstraint;
import ar.edu.unlp.pasae.tp_integrador.exceptions.GenotypeDecoderException;
import ar.edu.unlp.pasae.tp_integrador.services.GenotypeDecoderService;

@Service
public class GenotypeValidator implements ConstraintValidator<GenotypeConstraint, String> {
  @Autowired
  private GenotypeDecoderService genotypeDecoderService;

  @Override
  public boolean isValid(final String genotype, final ConstraintValidatorContext context) {

    try {
      this.getGenotypeDecoderService().decodeGenotype(genotype);

      return true;
    } catch (GenotypeDecoderException e) {
      context.disableDefaultConstraintViolation();

      context
        .buildConstraintViolationWithTemplate("Genotipo inválido: " + e.getErrorsMessage())
        .addConstraintViolation();

      return false;
    }
  }

  /**
   * @return the genotypeDecoderService
   */
  public GenotypeDecoderService getGenotypeDecoderService() {
    return genotypeDecoderService;
  }
}
