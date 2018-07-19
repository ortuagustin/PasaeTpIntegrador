package ar.edu.unlp.pasae.tp_integrador.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.services.NumericPhenotypeService;

@RestController
@RequestMapping("/numeric-phenotypes")
public class NumericPhenotypeController {
  @Autowired
  NumericPhenotypeService phenotypesService;

  @GetMapping(path = "/", produces = "application/json")
  public Collection<NumericPhenotypeDTO> index() {
    return this.getPhenotypesService().list().collect(Collectors.toList());
  }

  @GetMapping(path = "/{id}", produces = "application/json")
  public NumericPhenotypeDTO show(@PathVariable(value = "id") Long id) {
    return this.getPhenotypesService().find(id);
  }

  @DeleteMapping(path = "/{id}")
  public void delete(@PathVariable(value = "id") Long id) {
    this.getPhenotypesService().delete(id);
  }

  @PutMapping(path = "/", consumes = "application/json", produces = "application/json")
  public NumericPhenotypeDTO create(@RequestBody @Valid NumericPhenotypeDTO phenotype) {
      return this.getPhenotypesService().create(phenotype);
  }

  @PatchMapping(path = "/", consumes = "application/json")
  public NumericPhenotypeDTO update(@RequestBody @Valid NumericPhenotypeDTO phenotype) {
    return this.getPhenotypesService().update(phenotype);
  }

  private NumericPhenotypeService getPhenotypesService() {
    return this.phenotypesService;
  }
}