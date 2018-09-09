package ar.edu.unlp.pasae.tp_integrador.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import ar.edu.unlp.pasae.tp_integrador.validators.GenotypeValidator;

@Documented
@Constraint(validatedBy = GenotypeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface GenotypeConstraint {
  String message() default "Genotipo inválido";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
