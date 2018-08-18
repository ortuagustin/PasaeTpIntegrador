package ar.edu.unlp.pasae.tp_integrador.services;

import java.text.MessageFormat;
import java.util.stream.Stream;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype.CategoricPhenotypeBuilder;
import ar.edu.unlp.pasae.tp_integrador.repositories.CategoricPhenotypeRepository;
import ar.edu.unlp.pasae.tp_integrador.transformers.Transformer;

@Service
@SuppressWarnings("unused")
public class CategoricPhenotypeServiceImpl implements CategoricPhenotypeService {
	@Autowired
	private CategoricPhenotypeRepository phenotypeRepository;
	@Autowired
	private Transformer<CategoricPhenotype, CategoricPhenotypeDTO> transformer;

	@Override
	public CategoricPhenotypeDTO find(Long phenotypeId) throws EntityNotFoundException {
		final CategoricPhenotype phenotype = this.findPhenotypeById(phenotypeId);

		return this.getTransformer().toDTO(phenotype);
	}

	@Override
	public CategoricPhenotypeDTO findByName(String name) throws EntityNotFoundException {
		final CategoricPhenotype phenotype = this.getPhenotypeRepository().findByName(name).orElseThrow(
				() -> new EntityNotFoundException(MessageFormat.format("No Categoric Phenotype found with name {0}", name)));

		return this.getTransformer().toDTO(phenotype);
	}

	@Override
	public Page<CategoricPhenotypeDTO> list(int page, int sizePerPage, String sortField, String sortOrder,
			String search) {
		Sort.Direction sortDirection = (sortOrder.toLowerCase().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageRequest = this.gotoPage(page, sizePerPage, sortField, sortDirection); // Genero la pagina solicitada
		Page<CategoricPhenotype> result;

		if (search.equals("")) {
			result = this.getPhenotypeRepository().findAll(pageRequest);
		} else {
			result = this.getPhenotypeRepository().findByNameContainsIgnoreCase(search, pageRequest);
		}

		return result.map(each -> this.getTransformer().toDTO(each));
	}

	@Override
	public Stream<CategoricPhenotypeDTO> list() {
		return this.getPhenotypeRepository().findAll().stream().map(each -> this.getTransformer().toDTO(each));
	}

	@Override
	public CategoricPhenotypeDTO update(Long phenotypeId, CategoricPhenotypeDTO phenotype) {
		CategoricPhenotype entity = this.buildPhenotype(phenotype);
		entity.setId(phenotypeId);

		return this.save(entity);
	}

	@Override
	public CategoricPhenotypeDTO create(CategoricPhenotypeDTO phenotype) {
		CategoricPhenotype entity = this.buildPhenotype(phenotype);

		return this.save(entity);
	}

	@Override
	public void delete(Long phenotypeId) throws EntityNotFoundException {
		final CategoricPhenotype phenotype = this.findPhenotypeById(phenotypeId);

		this.getPhenotypeRepository().delete(phenotype);
	}

	@Override
	public Integer count() {
		return (int) this.getPhenotypeRepository().count();
	}

	@Override
	public void deleteAll() {
		this.getPhenotypeRepository().deleteAll();
	}

	private PageRequest gotoPage(int page, int sizePerPage, String sortField, Sort.Direction sortDirection) {
		return PageRequest.of(page, sizePerPage, sortDirection, sortField);
	}

	private CategoricPhenotypeDTO save(CategoricPhenotype phenotype) {
		phenotype = this.getPhenotypeRepository().save(phenotype);

		return this.getTransformer().toDTO(phenotype);
	}

	private CategoricPhenotype buildPhenotype(CategoricPhenotypeDTO phenotype) {
		final CategoricPhenotypeBuilder builder = CategoricPhenotype.builder();

		return builder
			.addName(phenotype.getName())
			.addValues(phenotype.getValues())
			.createPhenotype();
	}

	private CategoricPhenotype findPhenotypeById(Long phenotypeId) throws EntityNotFoundException {
		final CategoricPhenotype phenotype = this.getPhenotypeRepository().findById(phenotypeId)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Categoric Phenotype found with Id {0}", phenotypeId)));

		return phenotype;
	}

	private CategoricPhenotypeRepository getPhenotypeRepository() {
		return phenotypeRepository;
	}

	private void setPhenotypeRepository(CategoricPhenotypeRepository repository) {
		this.phenotypeRepository = repository;
	}

	private Transformer<CategoricPhenotype, CategoricPhenotypeDTO> getTransformer() {
		return transformer;
	}

	private void setTransformer(Transformer<CategoricPhenotype, CategoricPhenotypeDTO> transformer) {
		this.transformer = transformer;
	}
}
