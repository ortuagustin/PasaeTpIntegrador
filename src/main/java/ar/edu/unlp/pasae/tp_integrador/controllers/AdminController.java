package ar.edu.unlp.pasae.tp_integrador.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlp.pasae.tp_integrador.dtos.CustomUserDTO;
import ar.edu.unlp.pasae.tp_integrador.services.CustomUserService;

@RestController
@RequestMapping(path = "/admin", produces="application/json")
public class AdminController {
	@Autowired
	private CustomUserService customUserService;

	@GetMapping(path = "/user-list")
	public List<CustomUserDTO> listAction() {
		return this.getCustomUserService().list();
	}

	@PutMapping(path = "/user-add", consumes="application/json")
	public void addAction(@RequestBody @Valid CustomUserDTO person) {
		this.getCustomUserService().create(person);
	}

	@DeleteMapping(path = "/user-remove", consumes="application/json")
	public void addAction(@RequestBody Long id) {
		this.getCustomUserService().delete(id);
	}

	private CustomUserService getCustomUserService() {
		return this.customUserService;
	}

}
