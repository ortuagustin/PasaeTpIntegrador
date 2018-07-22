package ar.edu.unlp.pasae.tp_integrador.services;

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype;

import javax.persistence.EntityNotFoundException;

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
		final CategoricPhenotype phenotype = this.getPhenotypeRepository().findByName(name)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Categoric Phenotype found with name {0}", name)));

		return this.getTransformer().toDTO(phenotype);
	}

	@Override
	public Stream<CategoricPhenotypeDTO> list() {
		return this.getPhenotypeRepository().findAll().stream().map(each -> this.getTransformer().toDTO(each));
	}

	@Override
	public CategoricPhenotypeDTO update(CategoricPhenotypeDTO phenotype) {
		CategoricPhenotype entity = this.getPhenotypeRepository().save(this.getTransformer().toEntity(phenotype));

		return this.getTransformer().toDTO(entity);
	}

	@Override
	public CategoricPhenotypeDTO create(CategoricPhenotypeDTO phenotype) {
		CategoricPhenotype entity = this.getPhenotypeRepository().save(this.getTransformer().toEntity(phenotype));

		return this.getTransformer().toDTO(entity);
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
