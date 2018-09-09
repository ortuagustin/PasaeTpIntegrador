package ar.edu.unlp.pasae.tp_integrador.services;

public class GenotypeDecoderError {
  private Integer index;
  private String input;

  public GenotypeDecoderError(String input, Integer index) {
    super();
    this.input = input;
    this.index = index;
  }

  public Integer getIndex() {
    return index;
  }

  public String getInput() {
    return input;
  }

  @Override
  public String toString() {
    return "index: " + this.getIndex().toString() + "input: " + this.getInput();
  }
}