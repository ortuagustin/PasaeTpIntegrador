package ar.edu.unlp.pasae.tp_integrador;

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