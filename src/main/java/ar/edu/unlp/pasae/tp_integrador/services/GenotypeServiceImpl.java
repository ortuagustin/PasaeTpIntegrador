package ar.edu.unlp.pasae.tp_integrador.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.GenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Genotype;
import ar.edu.unlp.pasae.tp_integrador.repositories.GenotypeRepository;
import ar.edu.unlp.pasae.tp_integrador.transformers.GenotypeTransformer;

@Service
public class GenotypeServiceImpl implements GenotypeService {
	@Autowired
	private GenotypeTransformer genotypeTransformer;
	@Autowired
	private GenotypeRepository genotypeRepository;

	/**
	 * Crea un paciente
	 * @param Dto del genotipo a guardar
	 * @return dto con los datos del genotipo persistido
	 */
	@Override
	public GenotypeDTO create(GenotypeDTO genotypeDto) {
		// Genero un genotipo a partir del DTO
		Genotype genotype = this.getTransformer().toEntity(genotypeDto);

		// Guardo
		this.getRepository().save(genotype);

		// Devuelvo el DTO
		return this.getTransformer().toDTO(genotype);
	}

	/**
	 * Obtiene el transformer de genotipos
	 * @return Transformer de genotipos
	 */
	private GenotypeTransformer getTransformer() {
		return this.genotypeTransformer;
	}

	/**
	 * Obtiene el repositorio de genotipos
	 * @return Repositorio de genotipos
	 */
	private GenotypeRepository getRepository() {
		return this.genotypeRepository;
	}
}
