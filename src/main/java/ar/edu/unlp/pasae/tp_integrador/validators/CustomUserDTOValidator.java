package ar.edu.unlp.pasae.tp_integrador.validators;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.unlp.pasae.tp_integrador.dtos.CustomUserDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Role;
import ar.edu.unlp.pasae.tp_integrador.entities.RoleName;
import ar.edu.unlp.pasae.tp_integrador.exceptions.NotValidRoleException;

@Component
public class CustomUserDTOValidator implements Validator {
	/**
	 * Solo aplico esta validacion a la clase CustomUserDTO
	 */
	@Override
	public boolean supports(Class<?> clazz) {
		return CustomUserDTO.class.equals(clazz);
	}

	/**
	 * Valido el user recibido en el REST Controller.
	 * Tomo los valores de roles genericamente para no
	 * tener que agregar a mano cada uno de los valores Enum
	 * que se definen en la clase Role
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		// Obtengo el usuario a evaluar
		CustomUserDTO user = (CustomUserDTO) obj;

		// Chequeo los roles
		RoleName[] roles = RoleName.class.getEnumConstants(); // Obtengo todos los roles declarados
		List<Role> userRoles = user.getAuthorities(); // Obtengo los roles que le pasaron al usuario

		// Para cada rol que le dieron al usuario me fijo que realmente sea
		// un rol declarado en el sistema
		for (Role userRole : userRoles) {
			boolean exists = false;
			for (RoleName role : roles) {
				if (userRole.getName().equals(role.toString())) {
					exists = true; // Bien, este rol existe
					break; // Corto el ciclo
				}
			}

			// Si el rol no fue encontrado, informo
			if (!exists) {
				String message = new NotValidRoleException().getMessage();
				errors.reject(message, message);
			}
		}
	}

}