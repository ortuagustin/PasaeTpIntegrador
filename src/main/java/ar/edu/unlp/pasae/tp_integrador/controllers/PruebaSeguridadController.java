package ar.edu.unlp.pasae.tp_integrador.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;

@Controller
public class PruebaSeguridadController {
	@RequestMapping(value="/helloworld", method = RequestMethod.GET)
	public @ResponseBody String helloWorld(ModelMap model) {
		model.addAttribute("message", "Welcome to the Hello World page");
		return "helloworld";

	}

	@RequestMapping(value="/secured/home", method = RequestMethod.GET)
	public @ResponseBody String securedHome(ModelMap model) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CustomUser user = null;
		if (principal instanceof CustomUser) {
			user = ((CustomUser) principal);
		}

		String name = user.getUsername();
		model.addAttribute("username", name);
		model.addAttribute("message", "Welcome to the secured page");
		return "home";

	}
}
