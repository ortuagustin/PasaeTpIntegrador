package ar.edu.unlp.pasae.tp_integrador.exceptions;

/**
 * Excepcion que se lanza cuando un usuario ya existe en la DB
 */
public class UserExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public UserExistsException(final String message, final Exception exception) {
		super(message, exception);
	}
	
	public UserExistsException(final String message) {
		super(message);
	}
	
	public UserExistsException() {
		super("El usuario ya existe");
	}
}
