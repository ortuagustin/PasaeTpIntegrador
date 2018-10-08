package ar.edu.unlp.pasae.tp_integrador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeValueRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeValueRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Analysis;
import ar.edu.unlp.pasae.tp_integrador.entities.AnalysisGroup;
import ar.edu.unlp.pasae.tp_integrador.entities.AnalysisState;
import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;
import ar.edu.unlp.pasae.tp_integrador.entities.Role;
import ar.edu.unlp.pasae.tp_integrador.entities.RoleName;
import ar.edu.unlp.pasae.tp_integrador.exceptions.GenotypeDecoderException;
import ar.edu.unlp.pasae.tp_integrador.repositories.AnalysisRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.CustomUserRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.RoleRepository;
import ar.edu.unlp.pasae.tp_integrador.services.AnalysisService;
import ar.edu.unlp.pasae.tp_integrador.services.CategoricPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.NumericPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.PatientService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class AnalysisTests {
	@Autowired
	private CustomUserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	@Autowired
	private AnalysisService analysisService;
	@Autowired
	private AnalysisRepository analysisRepository;
	@Autowired
	private CategoricPhenotypeService categoricPhenotypeService;
	@Autowired
	private NumericPhenotypeService numericPhenotypeService;
	@Autowired
	private PatientService patientService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	private CustomUser admin;

	@Before
	public void createUser() {
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
		this.roleRepository.save(r1);
		this.roleRepository.save(r2);
		this.roleRepository.save(r3);
		this.roleRepository.save(r4);

		// Guardo los usuarios de prueba
		this.admin = this.userRepository.save(new CustomUser("admin", passwordEncoder.encode("admin"), "admin@hotmail.com", "admin", "admin", roles2));
		this.userRepository.save(new CustomUser("genaro", passwordEncoder.encode("prueba"), "genarocamele@hotmail.com", "genaro", "camele", roles));
		this.userRepository.save(new CustomUser("admin2", passwordEncoder.encode("admin2"), "admin2@hotmail.com", "admin2", "admin2", roles2));
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
	public void it_creates_new_draft_analysis_with_categoric_phenotype() throws GenotypeDecoderException {
		AnalysisRequestDTO request = new AnalysisRequestDTO("Description", this.patientsIds(), "Categoric", this.categoricPhenotypeId("Adenocarcinoma"), "rs111");
		Long analysisId = this.analysisService.create(request).getId();
		AnalysisDTO analysis = this.analysisService.find(analysisId);

		Assert.assertNotNull(analysis);
		Assert.assertNotNull(analysis.getDate());
		Assert.assertEquals("Description", analysis.getDescription());
		Assert.assertEquals(AnalysisState.PENDING, analysis.getState());
		Assert.assertEquals(this.categoricPhenotypeId("Adenocarcinoma"), analysis.getPhenotypeId());
		Assert.assertNull(analysis.getCutoffValue());
		Assert.assertEquals("Categoric", analysis.getPhenotypeKind());
	}

	@Test
	public void it_creates_new_draft_analysis_with_numeric_phenotype() throws GenotypeDecoderException {
		Long cutoffValue = 10L;

		AnalysisRequestDTO request = new AnalysisRequestDTO("Description", this.patientsIds(), "Numeric", this.numericPhenotypeId("Peso"), "rs111", cutoffValue);
		Long analysisId = this.analysisService.create(request).getId();
		AnalysisDTO analysis = this.analysisService.find(analysisId);

		Assert.assertNotNull(analysis);
		Assert.assertNotNull(analysis.getDate());
		Assert.assertEquals("Description", analysis.getDescription());
		Assert.assertEquals(AnalysisState.PENDING, analysis.getState());
		Assert.assertEquals(this.numericPhenotypeId("Peso"), analysis.getPhenotypeId());
		Assert.assertEquals("Numeric", analysis.getPhenotypeKind());
		Assert.assertEquals(cutoffValue, analysis.getCutoffValue());
	}

	@Test
	public void it_returns_one_analysis_groups_per_categoric_phenotype_value() throws GenotypeDecoderException {
		AnalysisRequestDTO request = new AnalysisRequestDTO("Description", this.patientsIds(), "Categoric", this.categoricPhenotypeId("Adenocarcinoma"), "rs333");
		Long analysisId = this.analysisService.create(request).getId();
		Analysis analysis = this.analysisRepository.findById(analysisId).get();

		Collection<AnalysisGroup> groups = analysis.getAnalysisGroups();
		Assert.assertEquals(groups.size(), this.categoricPhenotype("Adenocarcinoma").getValues().size());

		Optional<AnalysisGroup> group = analysis.getAnalysisGroup("micropapilar");
		Assert.assertTrue(group.isPresent());
		Assert.assertEquals(1, group.get().getPatients().size());
	}

	@Test
	public void it_returns_two_analysis_groups_when_using_numeric_phenotype() throws GenotypeDecoderException {
		AnalysisRequestDTO request = new AnalysisRequestDTO("Description", this.patientsIds(), "Numeric", this.numericPhenotypeId("Peso"), "rs111", 50L);
		Long analysisId = this.analysisService.create(request).getId();
		Analysis analysis = this.analysisRepository.findById(analysisId).get();

		Collection<AnalysisGroup> groups = analysis.getAnalysisGroups();
		Assert.assertEquals(groups.size(), 2);

		Optional<AnalysisGroup> highersGroup = analysis.getAnalysisGroup(">=");
		Optional<AnalysisGroup> lowersGroup = analysis.getAnalysisGroup("<");
		Assert.assertTrue(highersGroup.isPresent());
		Assert.assertTrue(lowersGroup.isPresent());
		Assert.assertEquals(1, highersGroup.get().getPatients().size());
		Assert.assertTrue(lowersGroup.get().getPatients().isEmpty());
	}
}
