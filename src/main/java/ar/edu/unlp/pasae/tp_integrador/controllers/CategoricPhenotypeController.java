package ar.edu.unlp.pasae.tp_integrador.controllers;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.services.CategoricPhenotypeService;

@RestController
@RequestMapping("/categoric-phenotypes")
public class CategoricPhenotypeController {
	@Autowired
	CategoricPhenotypeService phenotypesService;

	@GetMapping(path = "/", produces = "application/json")
	public Page<CategoricPhenotypeDTO> index(
			@RequestParam(value="newestPage", defaultValue="0") int page,
			@RequestParam(value="newestSizePerPage", defaultValue="10") int sizePerPage,
			@RequestParam(value="newestSortField", defaultValue="name") String sortField,
			@RequestParam(value="newestSortOrder", defaultValue="asc") String sortOrder,
			@RequestParam(value="search", defaultValue="") String search
			) {
		return this.getPhenotypesService().list(page, sizePerPage, sortField, sortOrder, search);
	}

	@GetMapping(path = "/all", produces = "application/json")
	public Collection<CategoricPhenotypeDTO> indexAll() {
		return this.getPhenotypesService().list().collect(Collectors.toList());
	}

	@GetMapping(path = "/{id}", produces = "application/json")
	public CategoricPhenotypeDTO show(@PathVariable(value = "id") Long id) {
		return this.getPhenotypesService().find(id);
	}

	@DeleteMapping(path = "/{id}")
	public void delete(@PathVariable(value = "id") Long id) {
		this.getPhenotypesService().delete(id);
	}

	@PutMapping(path = "/", consumes = "application/json", produces = "application/json")
	public CategoricPhenotypeDTO create(@RequestBody @Valid CategoricPhenotypeDTO phenotype) {
		return this.getPhenotypesService().create(phenotype);
	}

	@PatchMapping(path = "/{id}", consumes = "application/json")
	public CategoricPhenotypeDTO update(@PathVariable(value = "id") Long id, @RequestBody @Valid CategoricPhenotypeDTO phenotype) {
		return this.getPhenotypesService().update(id, phenotype);
	}

	private CategoricPhenotypeService getPhenotypesService() {
		return this.phenotypesService;
	}
}