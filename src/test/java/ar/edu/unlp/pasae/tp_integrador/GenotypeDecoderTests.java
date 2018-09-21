package ar.edu.unlp.pasae.tp_integrador;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.unlp.pasae.tp_integrador.entities.Genotype;
import ar.edu.unlp.pasae.tp_integrador.exceptions.GenotypeDecoderException;
import ar.edu.unlp.pasae.tp_integrador.services.GenotypeDecoderError;
import ar.edu.unlp.pasae.tp_integrador.services.GenotypeDecoderService;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class GenotypeDecoderTests {
  @Autowired
  private GenotypeDecoderService genotypeDecoderService;

  @Test
  public void it_correctly_decodes_one_genotype() throws GenotypeDecoderException {
    String input = "rs111 ac";

    List<Genotype> output = this.genotypeDecoderService.decodeGenotype(input);

    Assert.assertEquals(1, output.size());

    Genotype first = output.get(0);

    Assert.assertEquals("ac", first.getValue());
    Assert.assertEquals((Integer) 111, first.getSnp());
    Assert.assertEquals("a", first.getFatherValue());
    Assert.assertEquals("c", first.getMotherValue());
  }

  @Test
  public void it_correctly_decodes_two_genotypes() throws GenotypeDecoderException {
    String input = "rs111 ac\nrs1122 at";

    List<Genotype> output = this.genotypeDecoderService.decodeGenotype(input);

    Assert.assertEquals(2, output.size());

    Genotype first = output.get(0);
    Genotype second = output.get(1);

    Assert.assertEquals("ac", first.getValue());
    Assert.assertEquals((Integer) 111, first.getSnp());
    Assert.assertEquals("a", first.getFatherValue());
    Assert.assertEquals("c", first.getMotherValue());
    Assert.assertEquals("at", second.getValue());
    Assert.assertEquals((Integer) 1122, second.getSnp());
    Assert.assertEquals("a", second.getFatherValue());
    Assert.assertEquals("t", second.getMotherValue());
  }

  @Test
  public void it_fails_to_decode_with_invalid_input() {
    String input = "sdafasfasdasda";

    try {
      this.genotypeDecoderService.decodeGenotype(input);
    } catch (GenotypeDecoderException e) {
      List<GenotypeDecoderError> errors = e.getErrors();

      Assert.assertEquals(1, errors.size());

      GenotypeDecoderError decoderError = errors.get(0);

      Assert.assertEquals("sdafasfasdasda", decoderError.getInput());
      Assert.assertEquals((Integer) 0, decoderError.getIndex());
    }
  }

  @Test
  public void it_does_not_fail_with_empty_input() throws GenotypeDecoderException {
    List<Genotype> output = this.genotypeDecoderService.decodeGenotype("");

    Assert.assertEquals(0, output.size());
  }

  @Test
  public void it_fails_to_decode_with_multiple_invalid_inputs() {
    String input = "sdafasf\nasdasda";

    try {
      this.genotypeDecoderService.decodeGenotype(input);
    } catch (GenotypeDecoderException e) {
      List<GenotypeDecoderError> errors = e.getErrors();

      Assert.assertEquals(2, errors.size());

      GenotypeDecoderError firstError = errors.get(0);
      GenotypeDecoderError secondError = errors.get(1);

      Assert.assertEquals("sdafasf", firstError.getInput());
      Assert.assertEquals((Integer) 0, firstError.getIndex());

      Assert.assertEquals("asdasda", secondError.getInput());
      Assert.assertEquals((Integer) 1, secondError.getIndex());
    }
  }

  @Test
  public void it_fails_to_decode_with_mixed_valid_and_invalid_inputs() {
    String input = "sdafasf\nrs111 ac";

    try {
      this.genotypeDecoderService.decodeGenotype(input);
    } catch (GenotypeDecoderException e) {
      List<GenotypeDecoderError> errors = e.getErrors();

      Assert.assertEquals(1, errors.size());

      GenotypeDecoderError decoderError = errors.get(0);

      Assert.assertEquals("sdafasf", decoderError.getInput());
      Assert.assertEquals((Integer) 0, decoderError.getIndex());
    }
  }
}