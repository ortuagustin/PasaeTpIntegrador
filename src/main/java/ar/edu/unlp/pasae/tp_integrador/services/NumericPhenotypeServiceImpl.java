package ar.edu.unlp.pasae.tp_integrador.services;

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype.NumericPhenotypeBuilder;
import ar.edu.unlp.pasae.tp_integrador.repositories.NumericPhenotypeRepository;

import javax.persistence.EntityNotFoundException;
import ar.edu.unlp.pasae.tp_integrador.transformers.Transformer;

@Service
@SuppressWarnings("unused")
public class NumericPhenotypeServiceImpl implements NumericPhenotypeService {
	@Autowired
	private NumericPhenotypeRepository phenotypeRepository;
	@Autowired
	private Transformer<NumericPhenotype, NumericPhenotypeDTO> transformer;

	@Override
	public NumericPhenotypeDTO find(Long phenotypeId) throws EntityNotFoundException {
		final NumericPhenotype phenotype = this.findPhenotypeById(phenotypeId);

		return this.getTransformer().toDTO(phenotype);
	}

	@Override
	public NumericPhenotypeDTO findByName(String name) throws EntityNotFoundException {
		final NumericPhenotype phenotype = this.getPhenotypeRepository().findByName(name)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Numeric Phenotype found with name {0}", name)));

		return this.getTransformer().toDTO(phenotype);
	}

	private PageRequest gotoPage(int page, int sizePerPage, String sortField, Sort.Direction sortDirection) {
		return PageRequest.of(page, sizePerPage, sortDirection, sortField);
	}

	@Override
	public Page<NumericPhenotypeDTO> list(int page, int sizePerPage, String sortField, String sortOrder, String search) {
		Sort.Direction sortDirection = (sortOrder.toLowerCase().equals("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
		PageRequest pageRequest = this.gotoPage(page, sizePerPage, sortField, sortDirection); // Genero la pagina solicitada
		Page<NumericPhenotype> result;

		if (search.equals("")) {
			result = this.getPhenotypeRepository().findAll(pageRequest);
		} else {
			result = this.getPhenotypeRepository().findByNameContainsIgnoreCase(search, pageRequest);
		}

		return result.map(each -> this.getTransformer().toDTO(each));
	}

	@Override
	public Stream<NumericPhenotypeDTO> list() {
		return this.getPhenotypeRepository().findAll().stream().map(each -> this.getTransformer().toDTO(each));
	}

	@Override
	public NumericPhenotypeDTO update(Long phenotypeId, NumericPhenotypeDTO phenotype) {
		NumericPhenotype entity = this.buildPhenotype(phenotype);
		entity.setId(phenotypeId);

		return this.save(entity);
	}

	@Override
	public NumericPhenotypeDTO create(NumericPhenotypeDTO phenotype) {
		NumericPhenotype entity = this.buildPhenotype(phenotype);

		return this.save(entity);
	}

	@Override
	public void delete(Long phenotypeId) throws EntityNotFoundException {
		final NumericPhenotype phenotype = this.findPhenotypeById(phenotypeId);

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

	private NumericPhenotypeDTO save(NumericPhenotype phenotype) {
		phenotype = this.getPhenotypeRepository().save(phenotype);

		return this.getTransformer().toDTO(phenotype);
	}

	private NumericPhenotype buildPhenotype(NumericPhenotypeDTO phenotype) {
		final NumericPhenotypeBuilder builder = NumericPhenotype.builder();

		 return builder.addName(phenotype.getName())
			.createPhenotype();
	}

	private NumericPhenotype findPhenotypeById(Long phenotypeId) throws EntityNotFoundException {
		final NumericPhenotype phenotype = this.getPhenotypeRepository().findById(phenotypeId)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Numeric Phenotype found with Id {0}", phenotypeId)));

		return phenotype;
	}

	private NumericPhenotypeRepository getPhenotypeRepository() {
		return phenotypeRepository;
	}

	private void setPhenotypeRepository(NumericPhenotypeRepository repository) {
		this.phenotypeRepository = repository;
	}

	private Transformer<NumericPhenotype, NumericPhenotypeDTO> getTransformer() {
		return transformer;
	}

	private void setTransformer(Transformer<NumericPhenotype, NumericPhenotypeDTO> transformer) {
		this.transformer = transformer;
	}
}
