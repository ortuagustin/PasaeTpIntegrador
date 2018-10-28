package ar.edu.unlp.pasae.tp_integrador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeValueRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.GenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeValueRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PhenotypePredictionRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PhenotypePredictionResultDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.SnpDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;
import ar.edu.unlp.pasae.tp_integrador.entities.Role;
import ar.edu.unlp.pasae.tp_integrador.entities.RoleName;
import ar.edu.unlp.pasae.tp_integrador.exceptions.GenotypeDecoderException;
import ar.edu.unlp.pasae.tp_integrador.repositories.CustomUserRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.RoleRepository;
import ar.edu.unlp.pasae.tp_integrador.services.AnalysisService;
import ar.edu.unlp.pasae.tp_integrador.services.CategoricPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.NumericPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.PatientService;
import ar.edu.unlp.pasae.tp_integrador.services.PhenotypePredictionService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class PhenotypePredictionServiceTests {
	@Autowired
	private CustomUserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	@Autowired
	private AnalysisService analysisService;
	@Autowired
	private CategoricPhenotypeService categoricPhenotypeService;
	@Autowired
	private NumericPhenotypeService numericPhenotypeService;
	@Autowired
	private PatientService patientService;
	@Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private PhenotypePredictionService predictionService;

	private CustomUser admin;

	@Before
	public void createUser() {
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

	@Before
	public void createPhenotypes() {
		this.createCategoricPhenotype("Adenocarcinoma", "papilar", "micropapilar", "acinar", "mucinoso", "sólido");
		this.createNumericPhenotype("Peso");
	}

	@Before
	public void createPatients() throws GenotypeDecoderException {
		Long userId = this.admin.getId();
		String name = "Agustin";
		String surname = "Ortu";
		String dni = "37058719";
		String email = "agus@ortu.com";
		String genotype = "rs111 ac\nrs1122 at";

		PatientRequestDTO request = new PatientRequestDTO(userId, name, surname, dni, email, genotype);
		request.addCategoricPhenotype(new CategoricPhenotypeValueRequestDTO(this.categoricPhenotypeId("Adenocarcinoma"), 1L));
		request.addNumericPhenotype(new NumericPhenotypeValueRequestDTO(this.numericPhenotypeId("Peso"), 50L));
		this.patientService.create(request);
	}

	private Long createCategoricPhenotype(String name, String ... values) {
		CategoricPhenotypeDTO phenotype;

		try {
			phenotype = this.categoricPhenotypeService.findByName(name);
		} catch (EntityNotFoundException e) {
			CategoricPhenotypeDTO request = new CategoricPhenotypeDTO(name, this.asSet(values));
			phenotype = this.categoricPhenotypeService.create(request);
		}

		return phenotype.getId();
	}

	private Long createNumericPhenotype(String name) {
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

	private Long categoricPhenotypeId(String name) {
		return this.categoricPhenotype(name).getId();
	}

	private CategoricPhenotypeDTO categoricPhenotype(String name) {
		return this.categoricPhenotypeService.findByName(name);
	}

	private Long numericPhenotypeId(String name) {
		return this.numericPhenotypeService.findByName(name).getId();
	}

	private Set<Long> patientsIds() {
		return this.patientService.list().map(patient -> patient.getId()).collect(Collectors.toSet());
	}

  @Test
	public void it_predicts_categoric_phenotype_value() throws GenotypeDecoderException {
		AnalysisRequestDTO request = new AnalysisRequestDTO("Description", this.patientsIds(), "Numeric", this.numericPhenotypeId("Peso"), "rs111", 50L);
		Long analysisId = this.analysisService.create(request).getId();
		Collection<SnpDTO> snps = new ArrayList<>();

		snps.add(new SnpDTO("rs111", Math.random(), Math.random()));
		snps.add(new SnpDTO("rs4112", Math.random(), Math.random()));

		this.analysisService.draft(analysisId, snps);
		this.analysisService.publish(analysisId);

    Collection<GenotypeDTO> genotypes = new ArrayList<>();
    genotypes.add(new GenotypeDTO(112, "AC"));
    genotypes.add(new GenotypeDTO(4131, "TG"));

    PhenotypePredictionRequestDTO predictionRequest = new PhenotypePredictionRequestDTO(analysisId, genotypes);
    PhenotypePredictionResultDTO predictionResult = this.predictionService.predict(predictionRequest);

    Assert.assertEquals(this.numericPhenotypeId("Peso"), predictionResult.getPhenotypeId());
    Assert.assertEquals("Numeric", predictionResult.getPhenotypeKind());
    Assert.assertNotNull(predictionResult.getPhenotypeValue());
	}
}
