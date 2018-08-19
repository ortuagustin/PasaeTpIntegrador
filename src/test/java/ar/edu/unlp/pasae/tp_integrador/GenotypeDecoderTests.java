package ar.edu.unlp.pasae.tp_integrador;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ar.edu.unlp.pasae.tp_integrador.entities.Genotype;
import ar.edu.unlp.pasae.tp_integrador.services.GenotypeDecoderService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GenotypeDecoderTests {
  @Autowired
  private GenotypeDecoderService genotypeDecoderService;

  @Test
  public void it_correctly_decodes_genotype() {
    String input = "rs111acrs1122at";

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
}