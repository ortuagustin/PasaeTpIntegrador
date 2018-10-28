package ar.edu.unlp.pasae.tp_integrador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeValueRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeValueRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;
import ar.edu.unlp.pasae.tp_integrador.entities.Role;
import ar.edu.unlp.pasae.tp_integrador.entities.RoleName;
import ar.edu.unlp.pasae.tp_integrador.exceptions.GenotypeDecoderException;
import ar.edu.unlp.pasae.tp_integrador.repositories.CustomUserRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.RoleRepository;
import ar.edu.unlp.pasae.tp_integrador.services.CategoricPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.NumericPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.PathologyService;
import ar.edu.unlp.pasae.tp_integrador.services.PatientService;

@Component
@Profile("!test")
@Transactional
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
	private PathologyService pathologyService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private PatientService patientService;

	private CustomUser admin;

	@Override
	public void run(final ApplicationArguments args) throws Exception {
		this.createUsers();
		this.createPathologies();
		this.createPatients();
	}

	private void createPatients() throws GenotypeDecoderException {
		Long userId = this.admin.getId();
		String name = "Agustin";
		String surname = "Ortu";
		String dni = "37058719";
		String email = "agus@ortu.com";
		String genotype = "rs111 ac\nrs1122 at";

		PatientRequestDTO request = new PatientRequestDTO(userId, name, surname, dni, email, genotype);
		request.addCategoricPhenotype(new CategoricPhenotypeValueRequestDTO(this.categoricPhenotype("Adenocarcinoma"), 1L));
		request.addCategoricPhenotype(new CategoricPhenotypeValueRequestDTO(this.categoricPhenotype("Nivel de glucosa"), 2L));
		request.addCategoricPhenotype(new CategoricPhenotypeValueRequestDTO(this.categoricPhenotype("Color de pelo"), 4L));
		request.addNumericPhenotype(new NumericPhenotypeValueRequestDTO(this.numericPhenotype("Peso"), 50L));
		request.addNumericPhenotype(new NumericPhenotypeValueRequestDTO(this.numericPhenotype("Presión Arterial"), 100L));
		this.patientService.create(request);
	}

	private void createPathologies() {
		PathologyRequestDTO request;
		Set<Long> phenotypes;

		phenotypes = new HashSet<>();
		phenotypes.add(this.categoricPhenotype("Adenocarcinoma", "papilar", "micropapilar", "acinar", "mucinoso", "sólido"));
		request = new PathologyRequestDTO("Cancer de Pulmón", Collections.emptySet(), phenotypes);
		this.pathologyService.create(request);

		phenotypes = new HashSet<>();
		phenotypes.add(this.categoricPhenotype("Nivel de glucosa", "alto", "medio", "bajo"));
		request = new PathologyRequestDTO("Diabetes", Collections.emptySet(), phenotypes);
		this.pathologyService.create(request);

		phenotypes = new HashSet<>();
		phenotypes.add(this.categoricPhenotype("Color de pelo", "castaño", "rubio", "pelirrojo", "negro"));
		request = new PathologyRequestDTO("Caída de pelo", Collections.emptySet(), phenotypes);
		this.pathologyService.create(request);

		phenotypes = new HashSet<>();
		phenotypes.add(this.numericPhenotype("Peso"));
		request = new PathologyRequestDTO("Obesidad", phenotypes, Collections.emptySet());
		this.pathologyService.create(request);

		phenotypes = new HashSet<>();
		phenotypes.add(this.numericPhenotype("Presión Arterial"));
		request = new PathologyRequestDTO("Hipertensión", phenotypes, Collections.emptySet());
		this.pathologyService.create(request);
	}

	private Long categoricPhenotype(String name, String ... values) {
		CategoricPhenotypeDTO phenotype;

		try {
			phenotype = this.categoricPhenotypeService.findByName(name);
		} catch (EntityNotFoundException e) {
			CategoricPhenotypeDTO request = new CategoricPhenotypeDTO(name, this.asSet(values));
			phenotype = this.categoricPhenotypeService.create(request);
		}

		return phenotype.getId();
	}

	private Long numericPhenotype(String name) {
		NumericPhenotypeDTO phenotype;

		try {
			phenotype = this.numericPhenotypeService.findByName(name);
		} catch (EntityNotFoundException e) {
			NumericPhenotypeDTO request = new NumericPhenotypeDTO(name);
			phenotype = this.numericPhenotypeService.create(request);
		}

		return phenotype.getId();
	}

	private <T> Set<T> asSet(T[] values) {
		return new HashSet<T>(Arrays.asList(values));
	}

	private void createUsers() {
		Role rol_cientifico = new Role(RoleName.SCIENTIST.toString());
		Role rol_admin = new Role(RoleName.ADMIN.toString());
		Role rol_medico_clinico = new Role(RoleName.CLINICAL_DOCTOR.toString());
		Role rol_registrante = new Role(RoleName.REGISTER.toString());

		this.roleRepository.save(rol_cientifico);
		this.roleRepository.save(rol_admin);
		this.roleRepository.save(rol_medico_clinico);
		this.roleRepository.save(rol_registrante);

		List<Role> roles_cientifico = new ArrayList<>();
		roles_cientifico.add(rol_cientifico);

		List<Role> roles_registrante = new ArrayList<>();
		roles_registrante.add(rol_registrante);

		List<Role> roles_medico_clinico = new ArrayList<>();
		roles_registrante.add(rol_medico_clinico);

		List<Role> todos = new ArrayList<>();
		todos.add(rol_cientifico);
		todos.add(rol_admin);
		todos.add(rol_medico_clinico);
		todos.add(rol_registrante);

		this.admin = this.userRepository.save(new CustomUser("admin", passwordEncoder.encode("admin"), "admin@hotmail.com", "admin", "admin", todos));
		this.userRepository.save(new CustomUser("genaro", passwordEncoder.encode("genaro"), "genarocamele@hotmail.com", "genaro", "camele", roles_cientifico));
		this.userRepository.save(new CustomUser("agus", passwordEncoder.encode("agus"), "agus@gmail.com", "agus", "agus", roles_registrante));
		this.userRepository.save(new CustomUser("mati", passwordEncoder.encode("mati"), "mati@gmail.com", "mati", "mati", roles_medico_clinico));
	}
}
