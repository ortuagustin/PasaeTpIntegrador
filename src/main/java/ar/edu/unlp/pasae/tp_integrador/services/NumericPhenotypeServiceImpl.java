package ar.edu.unlp.pasae.tp_integrador.services;

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;
import ar.edu.unlp.pasae.tp_integrador.repositories.NumericPhenotypeRepository;

import javax.persistence.EntityNotFoundException;
import ar.edu.unlp.pasae.tp_integrador.transformers.Transformer;

@Service
public class NumericPhenotypeServiceImpl implements NumericPhenotypeService {
	@Autowired
	private NumericPhenotypeRepository repository;
	@Autowired
	private Transformer<NumericPhenotype, NumericPhenotypeDTO> transformer;

	@Override
	public NumericPhenotypeDTO find(Long id) {
		return this.getTransformer().toDTO(this.getRepository().findById(id).get());
	}

	@Override
	public NumericPhenotypeDTO findByName(String name) throws EntityNotFoundException {
		final NumericPhenotype phenotype = this.getRepository().findByName(name)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No NumericPhenotype found with name {0}", name)));

		return this.getTransformer().toDTO(phenotype);
	}

	@Override
	public Stream<NumericPhenotypeDTO> list() {
		return this.getRepository().findAll().stream().map(each -> this.getTransformer().toDTO(each));
	}

	@Override
	public NumericPhenotypeDTO update(NumericPhenotypeDTO NumericPhenotype) {
		NumericPhenotype phenotype = this.getRepository().save(this.getTransformer().toEntity(NumericPhenotype));

		return this.getTransformer().toDTO(phenotype);
	}

	@Override
	public NumericPhenotypeDTO create(NumericPhenotypeDTO NumericPhenotypeDTO) {
		NumericPhenotype phenotype = this.getRepository().save(this.getTransformer().toEntity(NumericPhenotypeDTO));

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

	private NumericPhenotypeRepository getRepository() {
		return repository;
	}

	private void setRepository(NumericPhenotypeRepository repository) {
		this.repository = repository;
	}

	private Transformer<NumericPhenotype, NumericPhenotypeDTO> getTransformer() {
		return transformer;
	}

	private void setTransformer(Transformer<NumericPhenotype, NumericPhenotypeDTO> transformer) {
		this.transformer = transformer;
	}
}
