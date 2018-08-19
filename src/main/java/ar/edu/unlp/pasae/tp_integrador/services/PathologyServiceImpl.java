package ar.edu.unlp.pasae.tp_integrador.services;

import java.text.MessageFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.Pathology;
import ar.edu.unlp.pasae.tp_integrador.entities.Pathology.PathologyBuilder;
import ar.edu.unlp.pasae.tp_integrador.repositories.CategoricPhenotypeRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.NumericPhenotypeRepository;
import ar.edu.unlp.pasae.tp_integrador.repositories.PathologyRepository;
import ar.edu.unlp.pasae.tp_integrador.transformers.Transformer;

@Service
@SuppressWarnings("unused")
public class PathologyServiceImpl implements PathologyService {
	@Autowired
	private PathologyRepository pathologyRepository;
	@Autowired
	private CategoricPhenotypeRepository categoricPhenotypesRepository;
	@Autowired
	private NumericPhenotypeRepository numericPhenotypeRepository;
	@Autowired
	private Transformer<Pathology, PathologyDTO> transformer;

	@Override
	public PathologyDTO find(Long pathologyId) throws EntityNotFoundException {
		final Pathology pathology = this.findPathologyById(pathologyId);

		return this.getTransformer().toDTO(pathology);
	}

	@Override
	public PathologyDTO findByName(String name) throws EntityNotFoundException {
		final Pathology phenotype = this.getPathologyRepository().findByName(name)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Pathology found with name {0}", name)));

		return this.getTransformer().toDTO(phenotype);
	}

	@Override
	public Stream<PathologyDTO> list() {
		return this.getPathologyRepository().findAll().stream().map(each -> this.getTransformer().toDTO(each));
	}

	private PageRequest gotoPage(int page, int sizePerPage, String sortField, Sort.Direction sortDirection) {
		return PageRequest.of(page, sizePerPage, sortDirection, sortField);
	}

	@Override
	public Page<PathologyDTO> list(int page, int sizePerPage, String sortField, String sortOrder, String search) {
		Sort.Direction sortDirection = (sortOrder.toLowerCase().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageRequest = this.gotoPage(page, sizePerPage, sortField, sortDirection); // Genero la pagina solicitada
		Page<Pathology> result;

		if (search.equals("")) {
			result = this.getPathologyRepository().findAll(pageRequest);
		} else {
			result = this.getPathologyRepository().findByNameContainsIgnoreCase(search, pageRequest);
		}

		return result.map(each -> this.getTransformer().toDTO(each));
	}

	public PathologyDTO update(Long pathologyId, PathologyRequestDTO pathology) {
		Pathology entity = this.buildPathology(pathology);
		entity.setId(pathologyId);

		return this.save(entity);
	}

	@Override
	public PathologyDTO create(PathologyRequestDTO pathology) {
		Pathology entity = this.buildPathology(pathology);

		return this.save(entity);
	}

	@Override
	public void delete(Long pathologyId) throws EntityNotFoundException {
		final Pathology pathology = this.findPathologyById(pathologyId);

		this.getPathologyRepository().delete(pathology);
	}

	@Override
	public Integer count() {
		return (int) this.getPathologyRepository().count();
	}

	private Pathology findPathologyById(Long pathologyId) throws EntityNotFoundException {
		final Pathology Pathology = this.getPathologyRepository().findById(pathologyId)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Pathology found with Id {0}", pathologyId)));

		return Pathology;
	}

	private PathologyDTO save(Pathology pathology) {
		pathology = this.getPathologyRepository().save(pathology);

		return this.getTransformer().toDTO(pathology);
	}

	private Pathology buildPathology(PathologyRequestDTO pathology) {
		final PathologyBuilder builder = Pathology.builder();

		 return builder.addName(pathology.getName())
			.addCategoricPhenotypes(this.findCategoricPhenotypes(pathology.getCategoricPhenotypes()))
			.addNumericPhenotypes(this.findNumericPhenotypes(pathology.getNumericPhenotypes()))
			.createPathology();
	}

	private Set<CategoricPhenotype> findCategoricPhenotypes(Set<Long> phenotypes) {
		Set<CategoricPhenotype> entities = new HashSet<CategoricPhenotype>();

		for (Long phenotypeId : phenotypes) {
			final CategoricPhenotype phenotype = this.getCategoricPhenotypesRepository().findById(phenotypeId)
				.orElseThrow(() -> new EntityNotFoundException(
					MessageFormat.format("No Categoric Phenotype found with id {0}", phenotypeId)));

			entities.add(phenotype);
		}

		return entities;
	}

	private Set<NumericPhenotype> findNumericPhenotypes(Set<Long> phenotypes) {
		Set<NumericPhenotype> entities = new HashSet<NumericPhenotype>();

		for (Long phenotypeId : phenotypes) {
			final NumericPhenotype phenotype = this.getNumericPhenotypeRepository().findById(phenotypeId)
				.orElseThrow(() -> new EntityNotFoundException(
					MessageFormat.format("No Numeric Phenotype found with id {0}", phenotypeId)));

			entities.add(phenotype);
		}

		return entities;
	}

	private PathologyRepository getPathologyRepository() {
		return pathologyRepository;
	}

	private void setRepository(PathologyRepository pathologyRepository) {
		this.pathologyRepository = pathologyRepository;
	}

	public NumericPhenotypeRepository getNumericPhenotypeRepository() {
		return numericPhenotypeRepository;
	}

	public void setNumericPhenotypeRepository(NumericPhenotypeRepository numericPhenotypeRepository) {
		this.numericPhenotypeRepository = numericPhenotypeRepository;
	}

	public CategoricPhenotypeRepository getCategoricPhenotypesRepository() {
		return categoricPhenotypesRepository;
	}

	public void setCategoricPhenotypesRepository(CategoricPhenotypeRepository categoricPhenotypesRepository) {
		this.categoricPhenotypesRepository = categoricPhenotypesRepository;
	}

	private Transformer<Pathology, PathologyDTO> getTransformer() {
		return transformer;
	}

	private void setTransformer(Transformer<Pathology, PathologyDTO> transformer) {
		this.transformer = transformer;
	}
}
