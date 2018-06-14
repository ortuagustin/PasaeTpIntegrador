package ar.edu.unlp.pasae.tp_integrador.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;
import ar.edu.unlp.pasae.tp_integrador.repositories.CustomUserRepository;

@Service
public class CustomUserServiceImpl implements CustomUserService { 
	@Autowired CustomUserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUser user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario"));
		return user;
	}

}
