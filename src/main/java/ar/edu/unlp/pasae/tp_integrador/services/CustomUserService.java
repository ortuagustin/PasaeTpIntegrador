package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import ar.edu.unlp.pasae.tp_integrador.dtos.CustomUserDTO;

public interface CustomUserService extends UserDetailsService {
	void create(CustomUserDTO user);
	void update(CustomUserDTO user);
	void delete(Long id);
	CustomUserDTO retrieve(Long id);
	List<CustomUserDTO> list();
}
