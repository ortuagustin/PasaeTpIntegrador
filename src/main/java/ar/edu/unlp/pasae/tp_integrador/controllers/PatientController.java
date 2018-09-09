package ar.edu.unlp.pasae.tp_integrador.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlp.pasae.tp_integrador.dtos.PatientDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.exceptions.GenotypeDecoderException;
import ar.edu.unlp.pasae.tp_integrador.services.PatientService;

@RestController
@RequestMapping("/patients")
public class PatientController {
	@Autowired
	PatientService patientsService;

	@GetMapping(path = "/", produces = "application/json")
	public Page<PatientDTO> indexPageable(
			@RequestParam(value="newestPage", defaultValue="0") int page,
			@RequestParam(value="newestSizePerPage", defaultValue="10") int sizePerPage,
			@RequestParam(value="newestSortField", defaultValue="name") String sortField,
			@RequestParam(value="newestSortOrder", defaultValue="asc") String sortOrder,
			@RequestParam(value="search", defaultValue="") String search
			) {
		return this.getPatientsService().list(page, sizePerPage, sortField, sortOrder, search);
	}

	@GetMapping(path = "/all", produces = "application/json")
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
	public Object create(@RequestBody @Valid PatientRequestDTO request) {
		try {
			return this.getPatientsService().create(request);
		} catch (GenotypeDecoderException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrors());
		}
	}

	@PatchMapping(path = "/{id}", consumes = "application/json")
	public Object update(@PathVariable(value = "id") Long id, @RequestBody @Valid PatientRequestDTO request) {
		try {
			return this.getPatientsService().update(id, request);
		} catch (GenotypeDecoderException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getErrors());
		}
	}

	private PatientService getPatientsService() {
		return this.patientsService;
	}
}