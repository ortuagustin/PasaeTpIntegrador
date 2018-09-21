package ar.edu.unlp.pasae.tp_integrador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.AnalysisState;
import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;
import ar.edu.unlp.pasae.tp_integrador.entities.Role;
import ar.edu.unlp.pasae.tp_integrador.exceptions.GenotypeDecoderException;
import ar.edu.unlp.pasae.tp_integrador.repositories.CustomUserRepository;
import ar.edu.unlp.pasae.tp_integrador.services.AnalysisService;
import ar.edu.unlp.pasae.tp_integrador.services.CategoricPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.NumericPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.PathologyService;
import ar.edu.unlp.pasae.tp_integrador.services.PatientService;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@Transactional
@Rollback(true)
public class AnalysisTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private PatientService patientService;

	@Autowired
	private PathologyService pathologyService;

	@Autowired
	private NumericPhenotypeService numericPhenotypeService;

	@Autowired
	private CategoricPhenotypeService categoricPhenotypeService;

	@Autowired
	private CustomUserRepository userRepository;

	@Autowired
	private AnalysisService analysisService;

	private PathologyDTO pathology;

	private CustomUser user;

	private NumericPhenotypeDTO weigth;

	private CategoricPhenotypeDTO color;

	private HashSet<Long> patientsIds;

	@Before
	public void createUser() {
		List<Role> roles = new ArrayList<Role>();
		this.user = userRepository.save(new CustomUser("tester", "tester", "tester@example.com", "test", "test", roles));
	}

	private Set<Long> getNumericPhenotypesIds() {
		return this.numericPhenotypeService.list().map(each -> each.getId()).collect(Collectors.toSet());
	}

	private Set<Long> getCategoricPhenotypesIds() {
		return this.categoricPhenotypeService.list().map(each -> each.getId()).collect(Collectors.toSet());
	}

	@Before
	public void createPathology() {
		PathologyRequestDTO request = new PathologyRequestDTO("Test", this.getNumericPhenotypesIds(), this.getCategoricPhenotypesIds());
		this.pathology = this.pathologyService.create(request);
	}

	@Before
	public void createPhenotypes() {
		Map<Long, String> values = new HashMap<Long, String>();
		values.put(1L, "Red");
		values.put(2L, "Green");
		values.put(3L, "Blue");

		this.color = this.categoricPhenotypeService.create(new CategoricPhenotypeDTO("Color", values));
		this.weigth = this.numericPhenotypeService.create(new NumericPhenotypeDTO("Weigth"));
	}

	@Before
	public void createPatients() throws GenotypeDecoderException {
		Long patientId;
		Long userId = this.user.getId();
		this.patientsIds = new HashSet<Long>();

		patientId  = this.patientService.create(new PatientRequestDTO(userId, "Agustin", "Ortu", "37058719", "ortu.agustin@gmail.com", "rs111 ac")).getId();
		this.patientsIds.add(patientId);

		patientId = this.patientService.create(new PatientRequestDTO(userId, "Genaro", "Camele", "12345678", "camele.genaro@gmail.com", "rs222 ac")).getId();
		this.patientsIds.add(patientId);
	}

	@Test
	public void it_creates_new_draft_analysis_with_categoric_phenotype() {
		Long pathologyId = this.pathology.getId();
		Set<String> snps = new HashSet<>();
		snps.add("rs111");
		snps.add("rs333");
		AnalysisRequestDTO request = new AnalysisRequestDTO(pathologyId, this.patientsIds, "Categoric", this.color.getId(), snps);
		Long analysisId = this.analysisService.create(request).getId();
		AnalysisDTO analysis = this.analysisService.find(analysisId);

		Assert.assertNotNull(analysis);
		Assert.assertNotNull(analysis.getDate());
		Assert.assertEquals(AnalysisState.PENDING, analysis.getState());
		Assert.assertTrue(analysis.getPatients().containsAll(this.patientsIds));
		Assert.assertEquals(pathologyId, analysis.getPathologyId());
		Assert.assertEquals(this.color.getId(), analysis.getPhenotypeId());
		Assert.assertNull(analysis.getCutoffValue());
		Assert.assertEquals("Categoric", analysis.getPhenotypeKind());
		Assert.assertTrue(analysis.getSnps().containsAll(snps));
	}

	@Test
	public void it_creates_new_draft_analysis_with_numeric_phenotype() {
		Long pathologyId = this.pathology.getId();
		Double cutoffValue = 10.0;
		Set<String> snps = new HashSet<>();
		snps.add("rs111");
		snps.add("rs333");
		AnalysisRequestDTO request = new AnalysisRequestDTO(pathologyId, this.patientsIds, "Numeric", this.weigth.getId(), snps, cutoffValue);
		Long analysisId = this.analysisService.create(request).getId();
		AnalysisDTO analysis = this.analysisService.find(analysisId);

		Assert.assertNotNull(analysis);
		Assert.assertNotNull(analysis.getDate());
		Assert.assertEquals(AnalysisState.PENDING, analysis.getState());
		Assert.assertTrue(analysis.getPatients().containsAll(this.patientsIds));
		Assert.assertEquals(pathologyId, analysis.getPathologyId());
		Assert.assertEquals(this.weigth.getId(), analysis.getPhenotypeId());
		Assert.assertEquals("Numeric", analysis.getPhenotypeKind());
		Assert.assertEquals(cutoffValue, analysis.getCutoffValue());
		Assert.assertTrue(analysis.getSnps().containsAll(snps));
	}
}
