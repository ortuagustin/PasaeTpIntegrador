package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.CustomUserDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;
import ar.edu.unlp.pasae.tp_integrador.repositories.CustomUserRepository;
import ar.edu.unlp.pasae.tp_integrador.transformers.CustomUserTransformer;

@Service
public class CustomUserServiceImpl implements CustomUserService { 
	@Autowired
	CustomUserRepository userRepository;
	@Autowired
	private CustomUserTransformer userTransformer;

	/**
	 * Este metodo es necesario para poder obtener los usuarios 
	 * en la autenticacion
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUser user = this.userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario"));
		return user;
	}	

	@Override
	public void create(CustomUserDTO userDto) {
		CustomUser user = this.getTransformer().toEntity(userDto);
		this.getUserRepository().save(user);
	}

	@Override
	public void update(CustomUserDTO user) {
		Optional<CustomUser> opUser = this.getUserRepository().findById(user.getId());
		CustomUser u = opUser.get();
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		u.setEmail(user.getEmail());
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		u.setAuthorities(user.getAuthorities());
		this.getUserRepository().save(u);
	}

	@Override
	public void delete(Long id) {
		this.getUserRepository().deleteById(id);
	}

	@Override
	public CustomUserDTO retrieve(Long id) {
		CustomUser user = this.getUserRepository().findById(id).get();
		return this.getTransformer().toDTO(user);
	}
	
	@Override
	public List<CustomUserDTO> list() {
		List<CustomUser> listPerson = this.getUserRepository().findAll();
		return this.getTransformer().transform(listPerson);
	}


	private CustomUserTransformer getTransformer() {
		return this.userTransformer;
	}
	
	private CustomUserRepository getUserRepository() {
		return this.userRepository;
	}
}
