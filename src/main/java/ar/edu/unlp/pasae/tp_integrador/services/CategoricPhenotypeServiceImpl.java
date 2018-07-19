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
public class CategoricPhenotypeServiceImpl implements CategoricPhenotypeService {
	@Autowired
	private CategoricPhenotypeRepository repository;
	@Autowired
	private Transformer<CategoricPhenotype, CategoricPhenotypeDTO> transformer;

	@Override
	public CategoricPhenotypeDTO find(Long id) {
		return this.getTransformer().toDTO(this.getRepository().findById(id).get());
	}

	@Override
	public CategoricPhenotypeDTO findByName(String name) throws EntityNotFoundException {
		final CategoricPhenotype phenotype = this.getRepository().findByName(name)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No CategoricPhenotype found with name {0}", name)));

		return this.getTransformer().toDTO(phenotype);
	}

	@Override
	public Stream<CategoricPhenotypeDTO> list() {
		return this.getRepository().findAll().stream().map(each -> this.getTransformer().toDTO(each));
	}

	@Override
	public CategoricPhenotypeDTO update(CategoricPhenotypeDTO CategoricPhenotype) {
		CategoricPhenotype phenotype = this.getRepository().save(this.getTransformer().toEntity(CategoricPhenotype));

		return this.getTransformer().toDTO(phenotype);
	}

	@Override
	public CategoricPhenotypeDTO create(CategoricPhenotypeDTO CategoricPhenotypeDTO) {
		CategoricPhenotype phenotype = this.getRepository().save(this.getTransformer().toEntity(CategoricPhenotypeDTO));

		return this.getTransformer().toDTO(phenotype);
	}

	@Override
	public void delete(Long id) {
		this.getRepository().deleteById(id);
	}

	@Override
	public Integer count() {
		return (int) this.getRepository().count();
	}

	private CategoricPhenotypeRepository getRepository() {
		return repository;
	}

	private void setRepository(CategoricPhenotypeRepository repository) {
		this.repository = repository;
	}

	private Transformer<CategoricPhenotype, CategoricPhenotypeDTO> getTransformer() {
		return transformer;
	}

	private void setTransformer(Transformer<CategoricPhenotype, CategoricPhenotypeDTO> transformer) {
		this.transformer = transformer;
	}
}
