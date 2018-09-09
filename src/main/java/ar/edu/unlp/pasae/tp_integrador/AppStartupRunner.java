package ar.edu.unlp.pasae.tp_integrador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;
import ar.edu.unlp.pasae.tp_integrador.entities.Role;
import ar.edu.unlp.pasae.tp_integrador.entities.RoleName;
import ar.edu.unlp.pasae.tp_integrador.repositories.CustomUserRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.RoleRepository;
import ar.edu.unlp.pasae.tp_integrador.services.CategoricPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.NumericPhenotypeService;

@Component
public class AppStartupRunner implements ApplicationRunner {
	@Autowired
	private CustomUserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private CategoricPhenotypeService categoricPhenotypeService;
	@Autowired
	private NumericPhenotypeService numericPhenotypeService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(final ApplicationArguments args) throws Exception {
		// Declaro los roles
		Role r1 = new Role(RoleName.SCIENTIST.toString());
		Role r2 = new Role(RoleName.ADMIN.toString());
		Role r3 = new Role(RoleName.CLINICAL_DOCTOR.toString());
		Role r4 = new Role(RoleName.REGISTER.toString());
		
		// Usuario cientifico de prueba
		List<Role> roles = new ArrayList<Role>();
		roles.add(r1);

		// Usuario admin de prueba
		List<Role> roles2 = new ArrayList<Role>();
		roles2.add(r2);

		// Guardo los elementos
		
		// Guardo los roles
		this.getRoleRepository().save(r1);
		this.getRoleRepository().save(r2);
		this.getRoleRepository().save(r3);
		this.getRoleRepository().save(r4);
		
		// Guardo los usuarios de prueba
		this.getUserRepository().save(new CustomUser("genaro", passwordEncoder.encode("prueba"), "genarocamele@hotmail.com",
				"genaro", "camele", roles));
		this.getUserRepository()
				.save(new CustomUser("admin", passwordEncoder.encode("admin"), "admin@hotmail.com", "admin", "admin", roles2));

		this.createMockPhenotypes();
		this.getUserRepository().save(
				new CustomUser("admin2", passwordEncoder.encode("admin2"), "admin2@hotmail.com", "admin2", "admin2", roles2));
	}

	/**
	 * Crea los Fenotipos Mock
	 */
	private void createMockPhenotypes() {
		for (Long i = 1L; i <= 10; i++) {
			CategoricPhenotypeDTO request;
			Map<Long, String> values = new HashMap<Long, String>();
			values.put(i, "Value " + i);

			request = new CategoricPhenotypeDTO("Phenotype #" + i, values);
			this.getCategoricPhenotypeService().create(request);
		}

		for (Integer i = 1; i <= 10; i++) {
			NumericPhenotypeDTO request;
			request = new NumericPhenotypeDTO("Phenotype #" + i);
			this.getNumericPhenotypeService().create(request);
		}
	}

	private NumericPhenotypeService getNumericPhenotypeService() {
		return numericPhenotypeService;
	}

	private CategoricPhenotypeService getCategoricPhenotypeService() {
		return categoricPhenotypeService;
	}

	private CustomUserRepository getUserRepository() {
		return userRepository;
	}

	private RoleRepository getRoleRepository() {
		return roleRepository;
	}
}
