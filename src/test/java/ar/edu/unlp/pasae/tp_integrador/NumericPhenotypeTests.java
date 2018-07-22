package ar.edu.unlp.pasae.tp_integrador;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.ValidationException;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;
import ar.edu.unlp.pasae.tp_integrador.services.NumericPhenotypeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class NumericPhenotypeTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private NumericPhenotypeService phenotypeService;

  @Test
	public void it_returns_phenotype_list() {
		Collection<NumericPhenotypeDTO> phenotypes;

		phenotypes = this.phenotypeService.list().collect(Collectors.toList());

		Assert.assertTrue("Phenotype list should be empty", phenotypes.isEmpty());

		for (Integer i = 1; i <= 10; i++) {
			NumericPhenotypeDTO request;
			request = new NumericPhenotypeDTO("Phenotype #" + i);
			this.phenotypeService.create(request);
		}

		phenotypes = this.phenotypeService.list().collect(Collectors.toList());

		Assert.assertFalse("Phenotype list should not be empty", phenotypes.isEmpty());
		Assert.assertEquals(10, phenotypes.size());
	}

	@Test
	public void it_returns_phenotype_count() {
		Assert.assertEquals((Integer) 0, this.phenotypeService.count());

		for (Integer i = 1; i <= 10; i++) {
			NumericPhenotypeDTO request;
			request = new NumericPhenotypeDTO("Phenotype #" + i);
			this.phenotypeService.create(request);
		}

		Assert.assertEquals((Integer) 10, this.phenotypeService.count());
	}

	@Test
	public void it_finds_phenotype_by_id() {
		String name = "A Phenotype";
		NumericPhenotypeDTO request = new NumericPhenotypeDTO(name);
		Long phenotypeId = this.phenotypeService.create(request).getId();
		NumericPhenotypeDTO phenotype = this.phenotypeService.find(phenotypeId);

		Assert.assertNotNull(phenotype);
		Assert.assertEquals(name, phenotype.getName());
	}

	@Test
	public void it_throws_exception_when_phenotype_id_does_not_exist() {
		thrown.expect(EntityNotFoundException.class);

		this.phenotypeService.find(1L);
		this.phenotypeService.find(2L);
	}

	@Test
	public void it_finds_phenotype_by_name() {
		String name = "Name";

		NumericPhenotypeDTO request = new NumericPhenotypeDTO(name);
		this.phenotypeService.create(request).getId();
		NumericPhenotypeDTO phenotype = this.phenotypeService.findByName(name);

		Assert.assertNotNull(phenotype);
		Assert.assertEquals(name, phenotype.getName());
	}

	@Test
	public void it_throws_exception_when_phenotype_name_does_not_exist() {
		thrown.expect(EntityNotFoundException.class);

		this.phenotypeService.findByName("");
		this.phenotypeService.findByName("123");
		this.phenotypeService.findByName("12345678");
	}

	@Test
	public void it_deletes_phenotype() {
		NumericPhenotypeDTO request = new NumericPhenotypeDTO("Name");
		Long phenotypeId = this.phenotypeService.create(request).getId();

		NumericPhenotypeDTO phenotype;

		phenotype = this.phenotypeService.find(phenotypeId);
		Assert.assertNotNull(phenotype);

		thrown.expect(EntityNotFoundException.class);

		this.phenotypeService.delete(phenotypeId);
		phenotype = this.phenotypeService.find(phenotypeId);
		Assert.assertNull(phenotype);
	}

	@Test
	public void it_throws_exception_when_deleting_non_existing_phenotype() {
		thrown.expect(EntityNotFoundException.class);

		this.phenotypeService.delete(1L);
		this.phenotypeService.delete(2L);
	}

	@Test
	public void it_saves_new_phenotype() {
		String name = "Name";

		NumericPhenotypeDTO request = new NumericPhenotypeDTO(name);
		NumericPhenotypeDTO phenotype = this.phenotypeService.create(request);

		Assert.assertNotNull(phenotype);
    Assert.assertEquals(name, phenotype.getName());
  }

	@Test
	public void it_updates_existing_phenotype() {
    NumericPhenotypeDTO createRequest = new NumericPhenotypeDTO("Name");
		NumericPhenotypeDTO phenotype = this.phenotypeService.create(createRequest);

    String name = "Changed Name";

		NumericPhenotypeDTO updateRequest = new NumericPhenotypeDTO(phenotype.getId(), name);
		NumericPhenotypeDTO updatedPhenotype = this.phenotypeService.update(updateRequest);

    Assert.assertEquals(name, updatedPhenotype.getName());
  }

  @Test
	public void it_does_not_allow_empty_name() {
		String name = "";
		NumericPhenotypeDTO request = new NumericPhenotypeDTO(name);

		thrown.expect(ValidationException.class);
		this.phenotypeService.create(request);
  }

  @Test
  public void it_returns_true_for_valid_values() {
    String name = "Name";

    NumericPhenotype phenotype = new NumericPhenotype(name);
    Assert.assertTrue(phenotype.validate("1"));
    Assert.assertTrue(phenotype.validate("1.00"));
    Assert.assertTrue(phenotype.validate("20"));
    Assert.assertTrue(phenotype.validate("3.50"));
    Assert.assertTrue(phenotype.validate("0.35"));
  }

  @Test
  public void it_returns_false_for_invalid_values() {
    String name = "Name";

    NumericPhenotype phenotype = new NumericPhenotype(name);
    Assert.assertFalse(phenotype.validate(""));
    Assert.assertFalse(phenotype.validate("aaa"));
    Assert.assertFalse(phenotype.validate("20aa"));
    Assert.assertFalse(phenotype.validate("3,50"));
    Assert.assertFalse(phenotype.validate("!&$%"));
  }
}