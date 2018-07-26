package ar.edu.unlp.pasae.tp_integrador;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.services.CategoricPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.NumericPhenotypeService;
import ar.edu.unlp.pasae.tp_integrador.services.PathologyService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class PathologyTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private PathologyService pathologyService;

	@Autowired
	private NumericPhenotypeService numericPhenotypeService;

	@Autowired
	private CategoricPhenotypeService categoricPhenotypeService;

	@Before
	public void createNumericPhenotypes() {
		for (Integer i = 1; i <= 10; i++) {
			NumericPhenotypeDTO request;
			request = new NumericPhenotypeDTO("Phenotype #" + i);
			this.numericPhenotypeService.create(request);
		}
	}

	@Before
	public void createCategoricPhenotypes() {
		for (Integer i = 1; i <= 3; i++) {
			CategoricPhenotypeDTO request;
			Set<String> values = new HashSet<String>();
			values.add("Value " + i);

			request = new CategoricPhenotypeDTO("Phenotype #" + i, values);
			this.categoricPhenotypeService.create(request);
		}
	}

	private Set<Long> getNumericPhenotypesIds() {
		return this.numericPhenotypeService.list().map(each -> each.getId()).collect(Collectors.toSet());
	}

	private Set<Long> getCategoricPhenotypesIds() {
		return this.categoricPhenotypeService.list().map(each -> each.getId()).collect(Collectors.toSet());
	}

	@Test
	public void it_returns_pathology_list() {
		Collection<PathologyDTO> pathologies;

		pathologies = this.pathologyService.list().collect(Collectors.toList());

		Assert.assertTrue("Pathologies should be empty", pathologies.isEmpty());

		for (Integer i = 1; i <= 8; i++) {
			PathologyRequestDTO request;
			String name = "Pathology " + i;

			request = new PathologyRequestDTO(name, this.getNumericPhenotypesIds(), this.getCategoricPhenotypesIds());
			this.pathologyService.create(request);
		}

		pathologies = this.pathologyService.list().collect(Collectors.toList());

		Assert.assertFalse("Pathologies should not be empty", pathologies.isEmpty());
		Assert.assertEquals(8, pathologies.size());
	}

	@Test
	public void it_returns_pathology_count() {
		Assert.assertEquals((Integer) 0, this.pathologyService.count());

		for (Integer i = 1; i <= 8; i++) {
			PathologyRequestDTO request;
			String name = "Pathology " + i;

			request = new PathologyRequestDTO(name, this.getNumericPhenotypesIds(), this.getCategoricPhenotypesIds());
			this.pathologyService.create(request);
		}

		Assert.assertEquals((Integer) 8, this.pathologyService.count());
	}

	@Test
	public void it_finds_pathology_by_id() {
		String name = "Name";

		PathologyRequestDTO request = new PathologyRequestDTO(name, this.getNumericPhenotypesIds(), this.getCategoricPhenotypesIds());
		Long pathologyId = this.pathologyService.create(request).getId();
		PathologyDTO pathology = this.pathologyService.find(pathologyId);

		Assert.assertNotNull(pathology);
		Assert.assertEquals(name, pathology.getName());
    Assert.assertEquals(this.getNumericPhenotypesIds().size(), pathology.getNumericPhenotypes().size());
    Assert.assertEquals(this.getCategoricPhenotypesIds().size(), pathology.getCategoricPhenotypes().size());
	}

	@Test
	public void it_throws_exception_when_pathology_id_does_not_exist() {
		thrown.expect(EntityNotFoundException.class);

		this.pathologyService.find(1L);
		this.pathologyService.find(2L);
	}

	@Test
	public void it_finds_pathology_by_name() {
		String name = "Name";

		PathologyRequestDTO request = new PathologyRequestDTO(name, this.getNumericPhenotypesIds(), this.getCategoricPhenotypesIds());
		this.pathologyService.create(request).getId();
		PathologyDTO pathology = this.pathologyService.findByName(name);

		Assert.assertNotNull(pathology);
		Assert.assertEquals(name, pathology.getName());
    Assert.assertEquals(this.getNumericPhenotypesIds().size(), pathology.getNumericPhenotypes().size());
    Assert.assertEquals(this.getCategoricPhenotypesIds().size(), pathology.getCategoricPhenotypes().size());
	}

	@Test
	public void it_throws_exception_when_pathology_name_does_not_exist() {
		thrown.expect(EntityNotFoundException.class);

		this.pathologyService.findByName("");
		this.pathologyService.findByName("123");
		this.pathologyService.findByName("12345678");
	}

	@Test
	public void it_deletes_pathology() {
		String name = "Name";

		PathologyRequestDTO request = new PathologyRequestDTO(name, this.getNumericPhenotypesIds(), this.getCategoricPhenotypesIds());
		Long pathologyId = this.pathologyService.create(request).getId();

		PathologyDTO pathology;

		pathology = this.pathologyService.find(pathologyId);
		Assert.assertNotNull(pathology);

		thrown.expect(EntityNotFoundException.class);

		this.pathologyService.delete(pathologyId);
		pathology = this.pathologyService.find(pathologyId);
		Assert.assertNull(pathology);
	}

	@Test
	public void it_throws_exception_when_deleting_non_existing_pathology() {
		thrown.expect(EntityNotFoundException.class);

		this.pathologyService.delete(1L);
		this.pathologyService.delete(2L);
	}

	@Test
	public void it_saves_new_pathology() {
		String name = "Name";

		PathologyRequestDTO request = new PathologyRequestDTO(name, this.getNumericPhenotypesIds(), this.getCategoricPhenotypesIds());
		PathologyDTO pathology = this.pathologyService.create(request);

		Assert.assertNotNull(pathology);
		Assert.assertEquals(name, pathology.getName());
    Assert.assertEquals(this.getNumericPhenotypesIds().size(), pathology.getNumericPhenotypes().size());
    Assert.assertEquals(this.getCategoricPhenotypesIds().size(), pathology.getCategoricPhenotypes().size());
	}

	@Test
	public void it_updates_existing_pathology() {
		PathologyRequestDTO createRequest = new PathologyRequestDTO("Name", this.getNumericPhenotypesIds(),
				this.getCategoricPhenotypesIds());

		PathologyDTO pathology = this.pathologyService.create(createRequest);

		String name = "Changed Name";

		PathologyRequestDTO updateRequest = new PathologyRequestDTO(name, this.getNumericPhenotypesIds(),
				this.getCategoricPhenotypesIds());

		PathologyDTO updatedPathology = this.pathologyService.update(pathology.getId(), updateRequest);

		Assert.assertEquals(name, updatedPathology.getName());
    Assert.assertEquals(this.getNumericPhenotypesIds().size(), pathology.getNumericPhenotypes().size());
    Assert.assertEquals(this.getCategoricPhenotypesIds().size(), pathology.getCategoricPhenotypes().size());
	}

	@Test
	public void it_does_not_allow_empty_name() {
		String name = "";

		PathologyRequestDTO request = new PathologyRequestDTO(name, this.getNumericPhenotypesIds(), this.getCategoricPhenotypesIds());

		thrown.expect(ValidationException.class);
		this.pathologyService.create(request);
	}
}
