package ar.edu.unlp.pasae.tp_integrador.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
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

		// RESPUESTA 2: esta bueno que tira un error 500 cuando el usuario existe? Que
		// otra manera estaria bueno manejarlo? MANEJAR EXCEPCIONES CON UN 400 GARANTIZANDO CAMPOS
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
	 * PRUEBA: Ejecuta el script en python
	 * @return Resultado del script de python
	 * @throws URISyntaxException 
	 */
	@GetMapping(path="/prueba-python")
	public String pruebaAction() throws URISyntaxException {
		String response = "";
		Process p;
		try {
			String pythonScriptURL = this.getClass().getClassLoader().getResource("script_prueba.py").toURI().getPath();
			System.out.println(pythonScriptURL);
			p = Runtime.getRuntime().exec("python " + pythonScriptURL);
			InputStream is = p.getInputStream();

			try(java.util.Scanner s = new java.util.Scanner(is)) {
				return s.useDelimiter("\\A").hasNext() ? s.next() : "";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response;
	}

	/**
	 * Obtiene el servicio de usuarios
	 * @return Servicio de usuarios
	 */
	private CustomUserService getCustomUserService() {
		return this.customUserService;
	}

}
