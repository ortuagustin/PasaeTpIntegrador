package ar.edu.unlp.pasae.tp_integrador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype;
import ar.edu.unlp.pasae.tp_integrador.services.CategoricPhenotypeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback(true)
public class CategoricPhenotypeTests {
	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Autowired
	private CategoricPhenotypeService phenotypeService;

	@Test
	public void it_saves_new_phenotype() {
		String name = "Name";
    Set<String> values = new HashSet<String>();
    values.add("Value 1");
    values.add("Value 2");
    values.add("Value 3");

		CategoricPhenotypeDTO request = new CategoricPhenotypeDTO(name, values);
		CategoricPhenotypeDTO phenotype = this.phenotypeService.create(request);

		Assert.assertNotNull(phenotype);
    Assert.assertEquals(name, phenotype.getName());
    Assert.assertEquals(values.size(), phenotype.getValues().size());
    Assert.assertTrue(phenotype.getValues().containsAll(values));
  }

	@Test
	public void it_updates_existing_phenotype() {
    Set<String> values = new HashSet<String>();
    values.add("Value 1");
    values.add("Value 2");
    values.add("Value 3");

    CategoricPhenotypeDTO createRequest = new CategoricPhenotypeDTO("Name", values);
		CategoricPhenotypeDTO phenotype = this.phenotypeService.create(createRequest);

    String name = "Changed Name";
    Set<String> updatedValues = new HashSet<String>();
    updatedValues.add("Value 4");
    updatedValues.add("Value 5");

		CategoricPhenotypeDTO updateRequest = new CategoricPhenotypeDTO(phenotype.getId(), name, updatedValues);
		CategoricPhenotypeDTO updatedPhenotype = this.phenotypeService.update(updateRequest);

    Assert.assertEquals(name, updatedPhenotype.getName());
    Assert.assertEquals(updatedValues.size(), updatedPhenotype.getValues().size());
    Assert.assertTrue(updatedPhenotype.getValues().containsAll(updatedValues));
  }

  @Test
	public void it_does_not_allow_empty_name() {
		String name = "";
    Set<String> values = new HashSet<String>();
    values.add("Value 1");
    values.add("Value 2");
    values.add("Value 3");

		CategoricPhenotypeDTO request = new CategoricPhenotypeDTO(name, values);

		thrown.expect(ValidationException.class);
		this.phenotypeService.create(request);
  }

  @Test
	public void it_does_not_allow_empty_values() {
		String name = "Name";
    Set<String> values = new HashSet<String>();

		CategoricPhenotypeDTO request = new CategoricPhenotypeDTO(name, values);

		thrown.expect(ValidationException.class);
		this.phenotypeService.create(request);
  }

  @Test
  public void it_returns_true_for_valid_values() {
    String name = "Name";
    Set<String> values = new HashSet<String>();
    values.add("Value 1");
    values.add("Value 2");
    values.add("Value 3");

    CategoricPhenotype phenotype = new CategoricPhenotype(name, values);
    Assert.assertTrue(phenotype.validate("Value 1"));
    Assert.assertTrue(phenotype.validate("Value 2"));
    Assert.assertTrue(phenotype.validate("Value 3"));
  }

  @Test
  public void it_returns_false_for_invalid_values() {
    String name = "Name";
    Set<String> values = new HashSet<String>();
    values.add("Value 1");
    values.add("Value 2");
    values.add("Value 3");

    CategoricPhenotype phenotype = new CategoricPhenotype(name, values);
    Assert.assertFalse(phenotype.validate(""));
    Assert.assertFalse(phenotype.validate("aaa"));
    Assert.assertFalse(phenotype.validate("Value1"));
  }
}