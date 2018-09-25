package ar.edu.unlp.pasae.tp_integrador;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Analysis;
import ar.edu.unlp.pasae.tp_integrador.entities.AnalysisGroup;
import ar.edu.unlp.pasae.tp_integrador.entities.AnalysisState;
import ar.edu.unlp.pasae.tp_integrador.entities.Patient;
import ar.edu.unlp.pasae.tp_integrador.repositories.AnalysisRepository;
import ar.edu.unlp.pasae.tp_integrador.services.AnalysisService;
import ar.edu.unlp.pasae.tp_integrador.services.CategoricPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.NumericPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.PathologyService;
import ar.edu.unlp.pasae.tp_integrador.services.PatientService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class AnalysisTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();
	@Autowired
	private PathologyService pathologyService;
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

	private Long pathologyId(String name) {
		return this.pathologyService.findByName(name).getId();
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
	public void it_creates_new_draft_analysis_with_categoric_phenotype() {
		Long pathologyId = this.pathologyId("Cancer de Pulmón");
		Set<String> snps = new HashSet<>();
		snps.add("rs111");
		snps.add("rs333");
		AnalysisRequestDTO request = new AnalysisRequestDTO(pathologyId, this.patientsIds(), "Categoric", this.categoricPhenotypeId("Adenocarcinoma"), snps);
		Long analysisId = this.analysisService.create(request).getId();
		AnalysisDTO analysis = this.analysisService.find(analysisId);

		Assert.assertNotNull(analysis);
		Assert.assertNotNull(analysis.getDate());
		Assert.assertEquals(AnalysisState.PENDING, analysis.getState());
		Assert.assertTrue(analysis.getPatients().containsAll(this.patientsIds()));
		Assert.assertEquals(this.categoricPhenotypeId("Adenocarcinoma"), analysis.getPhenotypeId());
		Assert.assertNull(analysis.getCutoffValue());
		Assert.assertEquals("Categoric", analysis.getPhenotypeKind());
		Assert.assertTrue(analysis.getSnps().containsAll(snps));
	}

	@Test
	public void it_creates_new_draft_analysis_with_numeric_phenotype() {
		Long pathologyId = this.pathologyId("Obesidad");
		Long cutoffValue = 10L;
		Set<String> snps = new HashSet<>();
		snps.add("rs111");
		snps.add("rs333");
		AnalysisRequestDTO request = new AnalysisRequestDTO(pathologyId, this.patientsIds(), "Numeric", this.numericPhenotypeId("Peso"), snps, cutoffValue);
		Long analysisId = this.analysisService.create(request).getId();
		AnalysisDTO analysis = this.analysisService.find(analysisId);

		Assert.assertNotNull(analysis);
		Assert.assertNotNull(analysis.getDate());
		Assert.assertEquals(AnalysisState.PENDING, analysis.getState());
		Assert.assertTrue(analysis.getPatients().containsAll(this.patientsIds()));
		Assert.assertEquals(this.numericPhenotypeId("Peso"), analysis.getPhenotypeId());
		Assert.assertEquals("Numeric", analysis.getPhenotypeKind());
		Assert.assertEquals(cutoffValue, analysis.getCutoffValue());
		Assert.assertTrue(analysis.getSnps().containsAll(snps));
	}

	@Test
	public void it_returns_one_analysis_groups_per_categoric_phenotype_value() {
		Long pathologyId = this.pathologyId("Cancer de Pulmón");
		Set<String> snps = new HashSet<>();
		snps.add("rs111");
		snps.add("rs333");
		AnalysisRequestDTO request = new AnalysisRequestDTO(pathologyId, this.patientsIds(), "Categoric", this.categoricPhenotypeId("Adenocarcinoma"), snps);
		Long analysisId = this.analysisService.create(request).getId();
		Analysis analysis = this.analysisRepository.findById(analysisId).get();

		Collection<AnalysisGroup> groups = analysis.getAnalysisGroups();
		Assert.assertEquals(groups.size(), this.categoricPhenotype("Adenocarcinoma").getValues().size());

		Optional<AnalysisGroup> group = analysis.getAnalysisGroup("micropapilar");
		Assert.assertTrue(group.isPresent());
		Assert.assertEquals(1, group.get().getPatients().size());
	}
}
