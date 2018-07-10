package ar.edu.unlp.pasae.tp_integrador.transformers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.Patient;

@Service
public class PatientTransformer implements Transformer<Patient, PatientDTO> {
	@Autowired
	private PhenotypeTransformer phenotypeTransformer;

	@Override
	public Patient toEntity(PatientDTO dto) {
		Patient entity = new Patient(dto.getId(), dto.getName(), dto.getSurname(), dto.getDni(), dto.getEmail(), dto.getUser());
		Set<NumericPhenotype> numericPhenotypes = this.getPhenotypeTransformer().numericToEntities(dto.getNumericPhenotypes());
		Set<CategoricPhenotype> categoricPhenotypes = this.getPhenotypeTransformer().categoricToEntities(dto.getCategoricPhenotypes());
		entity.setCategoricPhenotypes(categoricPhenotypes);
		entity.setNumericPhenotypes(numericPhenotypes);

		return entity;
	}

	@Override
	public PatientDTO toDTO(Patient entity) {
		PatientDTO dto =  new PatientDTO(entity.getId(), entity.getName(), entity.getSurname(), entity.getDni(), entity.getEmail(), entity.getUser());
		Set<NumericPhenotypeDTO> numericPhenotypes = this.getPhenotypeTransformer().numericToDtos(entity.getNumericPhenotypes());
		Set<CategoricPhenotypeDTO> categoricPhenotypes = this.getPhenotypeTransformer().categoricToDtos(entity.getCategoricPhenotypes());
		dto.setCategoricPhenotypes(categoricPhenotypes);
		dto.setNumericPhenotypes(numericPhenotypes);

		return dto;
	}

	/**
	 * @return the phenotypeTransformer
	 */
	public PhenotypeTransformer getPhenotypeTransformer() {
		return phenotypeTransformer;
	}

	/**
	 * @param phenotypeTransformer the phenotypeTransformer to set
	 */
	public void setPhenotypeTransformer(PhenotypeTransformer phenotypeTransformer) {
		this.phenotypeTransformer = phenotypeTransformer;
	}
}
