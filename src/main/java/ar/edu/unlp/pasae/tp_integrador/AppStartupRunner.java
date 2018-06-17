package ar.edu.unlp.pasae.tp_integrador;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;
import ar.edu.unlp.pasae.tp_integrador.entities.Role;
import ar.edu.unlp.pasae.tp_integrador.entities.RoleName;
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
		// Usuario cientifico de prueba
		List<Role> roles = new ArrayList<Role>();
		Role r1 = new Role(RoleName.SCIENTIST.toString());
		roles.add(r1);
		
		// Usuario admin de prueba
		List<Role> roles2 = new ArrayList<Role>();
		Role r2 = new Role(RoleName.ADMIN.toString());
		roles2.add(r2);
		
		
		// Guardo los elementos
		this.getRoleRepository().save(r1);
		this.getRoleRepository().save(r2);
		this.getUserRepository().save(new CustomUser("genaro", "prueba", "genarocamele@hotmail.com", "genaro", "camele", roles));
		this.getUserRepository().save(new CustomUser("admin", "admin", "admin@hotmail.com", "admin", "admin", roles2));
	}

	private CustomUserRepository getUserRepository() {
		return userRepository;
	}
	
	private RoleRepository getRoleRepository() {
		return roleRepository;
	}
}
