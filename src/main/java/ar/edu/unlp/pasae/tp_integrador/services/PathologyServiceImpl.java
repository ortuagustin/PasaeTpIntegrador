package ar.edu.unlp.pasae.tp_integrador.services;

import java.text.MessageFormat;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.PathologyDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Pathology;
import javax.persistence.EntityNotFoundException;
import ar.edu.unlp.pasae.tp_integrador.repositories.PathologyRepository;
import ar.edu.unlp.pasae.tp_integrador.transformers.Transformer;

@Service
@SuppressWarnings("unused")
public class PathologyServiceImpl implements PathologyService {
	@Autowired
	private PathologyRepository repository;
	@Autowired
	private Transformer<Pathology, PathologyDTO> transformer;

	@Override
	public PathologyDTO find(Long id) {
		return this.getTransformer().toDTO(this.getRepository().findById(id).get());
	}

	@Override
	public PathologyDTO findByName(String name) throws EntityNotFoundException {
		final Pathology phenotype = this.getRepository().findByName(name)
				.orElseThrow(() -> new EntityNotFoundException(
						MessageFormat.format("No Pathology found with name {0}", name)));

		return this.getTransformer().toDTO(phenotype);
	}

	@Override
	public Stream<PathologyDTO> list() {
		return this.getRepository().findAll().stream().map(each -> this.getTransformer().toDTO(each));
	}

	@Override
	public PathologyDTO update(PathologyDTO Pathology) {
		Pathology phenotype = this.getRepository().save(this.getTransformer().toEntity(Pathology));

		return this.getTransformer().toDTO(phenotype);
	}

	@Override
	public PathologyDTO create(PathologyDTO PathologyDTO) {
		Pathology phenotype = this.getRepository().save(this.getTransformer().toEntity(PathologyDTO));

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

	private PathologyRepository getRepository() {
		return repository;
	}

	private void setRepository(PathologyRepository repository) {
		this.repository = repository;
	}

	private Transformer<Pathology, PathologyDTO> getTransformer() {
		return transformer;
	}

	private void setTransformer(Transformer<Pathology, PathologyDTO> transformer) {
		this.transformer = transformer;
	}
}
