package ar.edu.unlp.pasae.tp_integrador.exceptions;

/**
 * Excepcion que se lanza cuando un rol solicitado no es valido.
 * Es decir, no existe/no esta declarado en el sistema
 */
public class NotValidRoleException extends Exception {

	private static final long serialVersionUID = 1L;

	public NotValidRoleException(final String message, final Exception exception) {
		super(message, exception);
	}

	public NotValidRoleException(final String message) {
		super(message);
	}

	public NotValidRoleException() {
		super("El rol no es valido");
	}
}
