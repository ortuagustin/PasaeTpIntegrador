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

import ar.edu.unlp.pasae.tp_integrador.dtos.PatientDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.services.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientController {
  @Autowired
  PatientService patientsService;

  @GetMapping(path = "/", produces = "application/json")
  public Collection<PatientDTO> index() {
    return this.getPatientsService().list().collect(Collectors.toList());
  }

  @GetMapping(path = "/{id}", produces = "application/json")
  public PatientDTO show(@PathVariable(value = "id") Long id) {
    return this.getPatientsService().find(id);
  }

  @DeleteMapping(path = "/{id}")
  public void delete(@PathVariable(value = "id") Long id) {
    this.getPatientsService().delete(id);
  }

  @PutMapping(path = "/", consumes = "application/json", produces = "application/json")
  public PatientDTO create(@RequestBody @Valid PatientRequestDTO request) {
      return this.getPatientsService().create(request);
  }

  @PatchMapping(path = "/", consumes = "application/json")
  public PatientDTO update(@RequestBody @Valid PatientRequestDTO request) {
    return this.getPatientsService().update(request);
  }

  private PatientService getPatientsService() {
    return this.patientsService;
  }
}