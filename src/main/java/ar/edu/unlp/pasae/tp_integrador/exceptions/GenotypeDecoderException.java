package ar.edu.unlp.pasae.tp_integrador.exceptions;

import java.util.List;
import java.util.ArrayList;

import ar.edu.unlp.pasae.tp_integrador.services.GenotypeDecoderError;

public class GenotypeDecoderException extends Exception {
	private static final long serialVersionUID = 1L;
	private List<GenotypeDecoderError> errors = new ArrayList<GenotypeDecoderError>();

  public GenotypeDecoderException(String message, List<GenotypeDecoderError> errors) {
    super(message);
    this.getErrors().addAll(errors);
  }

	public List<GenotypeDecoderError> getErrors() {
		return this.errors;
	}

	public String getErrorsMessage() {
		return this.getErrors().stream().map(each -> each.toString()).reduce("", String::concat);
	}
}