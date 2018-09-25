package ar.edu.unlp.pasae.tp_integrador.controllers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.AnalysisRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.exceptions.GenotypeDecoderException;
import ar.edu.unlp.pasae.tp_integrador.services.AnalysisService;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {
  @Autowired
  private AnalysisService analysisService;

  @GetMapping(path = "/pending", produces = "application/json")
  public Collection<AnalysisDTO> listPending() {
    return this.getAnalysisService().listPending().collect(Collectors.toList());
  }

  @GetMapping(path = "/draft", produces = "application/json")
  public Collection<AnalysisDTO> listDraft() {
    return this.getAnalysisService().listDraft().collect(Collectors.toList());
  }

  @GetMapping(path = "/published", produces = "application/json")
  public Collection<AnalysisDTO> listPublished() {
    return this.getAnalysisService().listPublished().collect(Collectors.toList());
  }
  
  @GetMapping(path = "/", produces = "application/json")
	public Page<AnalysisDTO> indexPageable(
			@RequestParam(value="newestPage", defaultValue="0") int page,
			@RequestParam(value="newestSizePerPage", defaultValue="10") int sizePerPage,
			@RequestParam(value="newestSortField", defaultValue="name") String sortField,
			@RequestParam(value="newestSortOrder", defaultValue="asc") String sortOrder,
			@RequestParam(value="search", defaultValue="") String search
			) {
		return this.getAnalysisService().list(page, sizePerPage, sortField, sortOrder, search);
	}


  @GetMapping(path = "/{id}", produces = "application/json")
  public AnalysisDTO show(@PathVariable(value = "id") Long id) {
    return this.getAnalysisService().find(id);
  }

  @PutMapping(path = "/", consumes = "application/json", produces = "application/json")
  public Object create(@RequestBody @Valid AnalysisRequestDTO analysis) {
		try {
			return this.getAnalysisService().create(analysis);
		} catch (GenotypeDecoderException e) {
			Map<String, Object> response = new HashMap<String, Object>();
			response.put("genotype_error", true);
			response.put("errors", e.getErrors());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
  }
  
  @PatchMapping(path = "/draft/{id}", consumes = "application/json")
  public AnalysisDTO draft(@PathVariable(value = "id") Long id) {
    return this.getAnalysisService().draft(id);
  }

  @PatchMapping(path = "/publish/{id}", consumes = "application/json")
  public AnalysisDTO publish(@PathVariable(value = "id") Long id) {
    return this.getAnalysisService().publish(id);
  }

  private AnalysisService getAnalysisService() {
    return this.analysisService;
  }
}