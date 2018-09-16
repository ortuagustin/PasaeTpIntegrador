package ar.edu.unlp.pasae.tp_integrador.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {
	/**
	 * Para obtener el usuario actual
	 * @return El usuario logueado actualmente
	 */
	@RequestMapping(value = "/currentUser", method = RequestMethod.GET)
	public Object currentUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		return auth.getPrincipal();
	}
}
