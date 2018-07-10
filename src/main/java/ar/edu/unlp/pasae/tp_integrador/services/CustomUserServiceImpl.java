package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.CustomUserDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;
import ar.edu.unlp.pasae.tp_integrador.entities.Role;
import ar.edu.unlp.pasae.tp_integrador.repositories.CustomUserRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.RoleRepository;
import ar.edu.unlp.pasae.tp_integrador.transformers.CustomUserTransformer;

@Service
public class CustomUserServiceImpl implements CustomUserService { 
	@Autowired
	CustomUserRepository userRepository;
	@Autowired
	private CustomUserTransformer userTransformer;
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * Este metodo es necesario para poder obtener los usuarios 
	 * en la autenticacion
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		CustomUser user = this.getUserRepository().findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No se encontró el usuario"));
		return user;
	}	

	@Override
	public CustomUserDTO create(CustomUserDTO userDto) {
		// PREGUNTAR: como hago para no tener que guardar instancias nuevas de Roles
		// cada vez que agrego una persona. O es buena practica hacerlo asi?
//		List<String> rolesToSearch = new ArrayList<String>(); // Lista con los nombres de los roles
//
//		// Obtengo los nombre de los roles 
//		for (Role role : userDto.getAuthorities()) {
//			rolesToSearch.add(role.getName());
//		}
//		
//		List<Role> roles = this.getRoleRepository().findAllByNameIn(rolesToSearch);
//		CustomUser user = this.getTransformer().toEntity(userDto);
//		user.setAuthorities(roles);
//		List<Role> roles = thsis.getRoleRepository().findAllByNameIn(user.getAuthorities())
		CustomUser user = this.getTransformer().toEntity(userDto);
		this.saveRoles(user);
		
		this.getUserRepository().save(user);
		return this.getTransformer().toDTO(user); // Devuelvo a DTO el usuario recien creado
	}

	/**
	 * Guarda en la DB los roles
	 * @param user Usuario al que se le va a pedir los roles para guardar
	 */
	private void saveRoles(CustomUser user) {
		List<Role> roles = user.getAuthorities();
		for (Role role : roles) {
			this.getRoleRepository().save(role);
		}
	}

	@Override
	public CustomUserDTO update(Long id, CustomUserDTO user) {
		Optional<CustomUser> opUser = this.getUserRepository().findById(id);
		CustomUser u = opUser.get();
		u.setUsername(user.getUsername());
		u.setPassword(user.getPassword());
		u.setEmail(user.getEmail());
		u.setFirstName(user.getFirstName());
		u.setLastName(user.getLastName());
		u.setAuthorities(user.getAuthorities());
		this.saveRoles(u); // Guardo los roles
		this.getUserRepository().save(u);
		return this.getTransformer().toDTO(u);
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
	
	/**
	 * Chequea si el usuario pasado por parametro existe
	 * @param user Usuario a evaluar
	 * @return True si existe, false en caso contrario
	 */
	@Override
	public boolean userExists(CustomUserDTO user) {
		return this.getUserRepository().findByUsername(user.getUsername()).isPresent();
	}
	
	/**
	 * Chequea si el usuario existe
	 * @param id Id del usuario a chequear
	 * @return True si el usuario con el id existe, false caso contrario
	 */
	@Override
	public boolean userExists(Long id) {
		return this.getUserRepository().findById(id).isPresent();
	}


	private CustomUserTransformer getTransformer() {
		return this.userTransformer;
	}
	
	private CustomUserRepository getUserRepository() {
		return this.userRepository;
	}
	
	private RoleRepository getRoleRepository() {
		return this.roleRepository;
	}

}
