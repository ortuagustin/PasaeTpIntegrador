package ar.edu.unlp.pasae.tp_integrador.services;

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.PhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Phenotype;
import javax.persistence.EntityNotFoundException;
import ar.edu.unlp.pasae.tp_integrador.repositories.PhenotypeRepository;
import ar.edu.unlp.pasae.tp_integrador.transformers.Transformer;

@Service
public class PhenotypeServiceImpl implements PhenotypeService {
	@Autowired
	private PhenotypeRepository repository;
	@Autowired
	private Transformer<Phenotype, PhenotypeDTO> transformer;

	@Override
	public PhenotypeDTO find(Long id) {
		return this.getTransformer().toDTO(this.getRepository().findById(id).get());
	}

	@Override
	public PhenotypeDTO findByName(String name) throws EntityNotFoundException {
		final Phenotype phenotype = this.getRepository().findByName(name)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Phenotype found with name {0}", name)));

		return this.getTransformer().toDTO(phenotype);
	}

	@Override
	public Stream<PhenotypeDTO> list() {
		return this.getRepository().findAll().stream().map(each -> this.getTransformer().toDTO(each));
	}

	@Override
	public PhenotypeDTO update(PhenotypeDTO Phenotype) {
		Phenotype phenotype = this.getRepository().save(this.getTransformer().toEntity(Phenotype));

		return this.getTransformer().toDTO(phenotype);
	}

	@Override
	public PhenotypeDTO create(PhenotypeDTO PhenotypeDTO) {
		Phenotype phenotype = this.getRepository().save(this.getTransformer().toEntity(PhenotypeDTO));

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

	private PhenotypeRepository getRepository() {
		return repository;
	}

	private void setRepository(PhenotypeRepository repository) {
		this.repository = repository;
	}

	private Transformer<Phenotype, PhenotypeDTO> getTransformer() {
		return transformer;
	}

	private void setTransformer(Transformer<Phenotype, PhenotypeDTO> transformer) {
		this.transformer = transformer;
	}
}
