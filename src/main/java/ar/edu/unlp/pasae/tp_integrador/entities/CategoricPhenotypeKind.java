package ar.edu.unlp.pasae.tp_integrador.entities;

// TODO modelar la lista posibles de valores
// TOOD validar que el "valor" pertenezca a la lista de valores
public class CategoricPhenotypeKind extends PhenotypeKind {
	private String value;

	/**
	 * @return the value
	 */
	public String getValue() {
    return value;
  }

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
    this.value = value;
  }
}