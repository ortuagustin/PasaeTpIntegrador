package ar.edu.unlp.pasae.tp_integrador.services;

import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetailsService;

import ar.edu.unlp.pasae.tp_integrador.dtos.CustomUserDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;

public interface CustomUserService extends UserDetailsService {
	CustomUserDTO create(CustomUserDTO user);
	CustomUserDTO update(Long id, CustomUserDTO user);
	void delete(Long id);
	CustomUserDTO retrieve(Long id);
	Page<CustomUser> list(int page, int sizePerPage, String sortField, String sortOrder);
	boolean userExists(CustomUserDTO user);
	boolean userExists(Long id);
}
