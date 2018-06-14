package ar.edu.unlp.pasae.tp_integrador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;
import ar.edu.unlp.pasae.tp_integrador.entities.Role;
import ar.edu.unlp.pasae.tp_integrador.repositories.CustomUserRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.RoleRepository;

@Component
public class AppStartupRunner implements ApplicationRunner {
	@Autowired
	private CustomUserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public void run(final ApplicationArguments args) throws Exception {
		// Usuario de prueba		
		List<Role> roles = new ArrayList<Role>();
		Role r1 = new Role("ROLE_USER");
		this.getRoleRepository().save(r1);
		roles.add(r1);
		this.getUserRepository().save(new CustomUser("genaro", "prueba", "genarocamele@hotmail.com", "genaro", "camele", roles));
	}

	private CustomUserRepository getUserRepository() {
		return userRepository;
	}
	
	private RoleRepository getRoleRepository() {
		return roleRepository;
	}
}
