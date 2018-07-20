package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
	
	private List<Role> getRoles(List<Role> userAuthorities) {
		List<Role> newRoles = new ArrayList<Role>(); // Lista con los nombres de los roles

		// Busco si existe, y si no existen los agrego
		for (Role role : userAuthorities) {
			Role existingRole = this.getRoleRepository().findOneByName(role.getName()).orElse(null);
			// Si no existe lo agrego
			if (existingRole == null) {
				this.getRoleRepository().save(role);
				newRoles.add(role);
			} else {
				newRoles.add(existingRole);
			}
		}
		
		return newRoles;
	}

	@Override
	public CustomUserDTO create(CustomUserDTO userDto) {
		List<Role> roles = this.getRoles(userDto.getAuthorities());
		CustomUser user = this.getTransformer().toEntity(userDto);
		user.setAuthorities(roles); // Asigno los roles
		
		this.getUserRepository().save(user);
		return this.getTransformer().toDTO(user); // Devuelvo a DTO el usuario recien creado
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
		u.setAuthorities(this.getRoles(user.getAuthorities()));
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
	
	/**
	 * Genera un Pageable para la paginacion y ordenamiento de 
	 * los usuarios requeridos
	 * @param Pagina actual para obtener desde la DB
	 * @return La pagina solicitada con el ordenamiento incluido
	 */
	private PageRequest gotoPage(int page, int sizePerPage, String sortField, Sort.Direction sortDirection) {
	    return PageRequest.of(page, sizePerPage, sortDirection, sortField);
	}
	
	@Override
	public Page<CustomUser> list(int page, int sizePerPage, String sortField, String sortOrder, String search) {
		Sort.Direction sortDirection = (sortOrder.toLowerCase().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC; 
		PageRequest pageRequest = this.gotoPage(page, sizePerPage, sortField, sortDirection); // Genero la pagina solicitada
		CustomUserRepository userRepository = this.getUserRepository();
		if (search.equals("")) {
			return userRepository.findAll(pageRequest);
		}
		return userRepository.findByUsernameContainingOrFirstNameContainingOrLastNameContaining(search, search, search, pageRequest);
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
