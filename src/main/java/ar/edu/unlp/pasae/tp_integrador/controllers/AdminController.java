package ar.edu.unlp.pasae.tp_integrador.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlp.pasae.tp_integrador.dtos.CustomUserDTO;
import ar.edu.unlp.pasae.tp_integrador.exceptions.UserExistsException;
import ar.edu.unlp.pasae.tp_integrador.exceptions.UserNotExistsException;
import ar.edu.unlp.pasae.tp_integrador.services.CustomUserService;
import ar.edu.unlp.pasae.tp_integrador.validators.CustomUserDTOValidator;

@RestController
@RequestMapping(path = "/admin", produces="application/json")
public class AdminController {
	@Autowired
	private CustomUserService customUserService;
	
	/**
	 * Bindeo el validador
	 */
	@InitBinder
	protected void initBinder(final WebDataBinder binder) {
		binder.addValidators(new CustomUserDTOValidator());
	}

	/**
	 * Lista todos los usuarios
	 * @return Lista de usuarios disponibles
	 */
	@GetMapping(path = "/users/")
	public List<CustomUserDTO> listAction() {
		return this.getCustomUserService().list();
	}

	/**
	 * Agrega un usuario
	 * @param user Usuario a agregar
	 * @return Un objeto vacio si no se pudo crear, o el usuario creado en caso contrario
	 * @throws UserExistsException En caso de que exista el usuario que se intenta agregar
	 */
	@PutMapping(path = "/users/", consumes="application/json")
	public CustomUserDTO addAction(@RequestBody @Valid CustomUserDTO user) throws UserExistsException {
		CustomUserService userService = this.getCustomUserService();
		if (userService.userExists(user)) {
			throw new UserExistsException();
		}
		
		// PREGUNTA 1: devuelvo un DTO con un ID (no me acuerdo por que), esto es una mala practica?
		// PREGUNTA 2: esta bueno que tira un error 500 cuando el usuario existe? Que
		// otra manera estaria bueno manejarlo?
		return this.getCustomUserService().create(user);
	}
	
	/**
	 * Edita el usuario con el id pasado por parametro
	 * @param id Id del usuario a modificar
	 * @param user Usuario con todos los campos para hacer el update
	 * @return El usuario recien modificado
	 * @throws UserNotExistsException En caso de que el usuario a modificar no exista
	 */
	@PatchMapping(path = "/users/{id}", consumes="application/json")
	public CustomUserDTO updateAction(@PathVariable(value="id") Long id, @RequestBody @Valid CustomUserDTO user) throws UserNotExistsException {
		CustomUserService userService = this.getCustomUserService();
		if (!userService.userExists(id)) {
			throw new UserNotExistsException();
		}
		
		return this.getCustomUserService().update(id, user);
	}

	/**
	 * Borra un usuario especifico
	 * @param id ID del usuario a eliminar de la DB
	 */
	@DeleteMapping(path = "/users/{id}")
	public void addAction(@PathVariable(value = "id") Long id) {
		this.getCustomUserService().delete(id);
	}

	/**
	 * Obtiene el servicio de usuarios
	 * @return Servicio de usuarios
	 */
	private CustomUserService getCustomUserService() {
		return this.customUserService;
	}

}
