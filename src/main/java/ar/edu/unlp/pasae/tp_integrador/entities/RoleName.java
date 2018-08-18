package ar.edu.unlp.pasae.tp_integrador.entities;

/**
 * Roles existentes en el sistema, no deben contener la palabra "role"
 * ya que el security provider lo pone implicitamente
 */
public enum RoleName {
	ADMIN, REGISTER, SCIENTIST, CLINICAL_DOCTOR;
}
