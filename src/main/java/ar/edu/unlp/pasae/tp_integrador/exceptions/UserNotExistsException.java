package ar.edu.unlp.pasae.tp_integrador.exceptions;

/**
 * Excepcion que se lanza cuando un usuario no existe en la DB
 */
public class UserNotExistsException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserNotExistsException(final String message, final Exception exception) {
		super(message, exception);
	}
	
	public UserNotExistsException(final String message) {
		super(message);
	}
	
	public UserNotExistsException() {
		super("El usuario no existe");
	}
}
