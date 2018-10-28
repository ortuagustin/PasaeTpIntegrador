package ar.edu.unlp.pasae.tp_integrador;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.unlp.pasae.tp_integrador.entities.Genotype;
import ar.edu.unlp.pasae.tp_integrador.exceptions.GenotypeDecoderException;
import ar.edu.unlp.pasae.tp_integrador.services.GenotypeDecoderError;
import ar.edu.unlp.pasae.tp_integrador.services.GenotypeDecoderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SnpDecoderTests {
  @Autowired
  private GenotypeDecoderService genotypeDecoderService;

  @Test
  public void it_correctly_decodes_one_snp() throws GenotypeDecoderException {
    String input = "rs111";

    List<String> output = this.genotypeDecoderService.decodeSnps(input);

    Assert.assertEquals(1, output.size());

    String first = output.get(0);

    Assert.assertEquals("rs111", first);
  }

  @Test
  public void it_correctly_decodes_two_genotypes() throws GenotypeDecoderException {
    String input = "rs111\nrs1122";

    List<String> output = this.genotypeDecoderService.decodeSnps(input);

    Assert.assertEquals(2, output.size());

    String first = output.get(0);
    String second = output.get(1);

    Assert.assertEquals("rs111", first);
    Assert.assertEquals("rs1122", second);
  }

  @Test
  public void it_fails_to_decode_with_invalid_input() {
    String input = "sdafasfasdasda";

    try {
      this.genotypeDecoderService.decodeSnps(input);
    } catch (GenotypeDecoderException e) {
      List<GenotypeDecoderError> errors = e.getErrors();

      Assert.assertEquals(1, errors.size());

      GenotypeDecoderError decoderError = errors.get(0);

      Assert.assertEquals("sdafasfasdasda", decoderError.getInput());
      Assert.assertEquals((Integer) 0, decoderError.getIndex());
    }
  }

  @Test
  public void it_fails_to_decode_with_invalid_input2() {
    String input = "rs111\nrs1122 AADDSA";
    Boolean exception = false;

    try {
      this.genotypeDecoderService.decodeSnps(input);
    } catch (GenotypeDecoderException e) {
      exception = true;
    }

    Assert.assertTrue(exception);
  }

  @Test
  public void it_does_not_fail_with_empty_input() throws GenotypeDecoderException {
    List<String> output = this.genotypeDecoderService.decodeSnps("");

    Assert.assertEquals(0, output.size());
  }

  @Test
  public void it_fails_to_decode_with_multiple_invalid_inputs() {
    String input = "sdafasf\nasdasda";

    try {
      this.genotypeDecoderService.decodeSnps(input);
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
    String input = "sdafasf\nrs111";

    try {
      this.genotypeDecoderService.decodeSnps(input);
    } catch (GenotypeDecoderException e) {
      List<GenotypeDecoderError> errors = e.getErrors();

      Assert.assertEquals(1, errors.size());

      GenotypeDecoderError decoderError = errors.get(0);

      Assert.assertEquals("sdafasf", decoderError.getInput());
      Assert.assertEquals((Integer) 0, decoderError.getIndex());
    }
  }
}