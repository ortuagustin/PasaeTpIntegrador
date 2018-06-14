package ar.edu.unlp.pasae.tp_integrador.entities;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

public class NumericPhenotypeKind extends PhenotypeKind {
  @NumberFormat(style = Style.NUMBER)
  private Integer value;

	/**
	 * @return the value
	 */
	public String getValue() {
    return value.toString();
  }

	/**
	 * @param value the value to set
	 */
  public void setValue(String value) {
    this.value = Integer.parseInt(value);
  }
}