package ar.edu.unlp.pasae.tp_integrador.transformers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Patient;
import ar.edu.unlp.pasae.tp_integrador.entities.Patient.PatientBuilder;

@Service
public class PatientTransformer extends BaseTransformer<Patient, PatientDTO> {
	@Autowired
	private PhenotypeTransformer phenotypeTransformer;

	@Override
	public Patient toEntity(PatientDTO dto) {
		// TODO: en realidad no se usa mas ... este metodo se va a terminar sacando de la interfaz Transformer
		final PatientBuilder builder = Patient.builder();

		 return builder.addName(dto.getName())
			.addSurname(dto.getSurname())
			.addEmail(dto.getEmail())
			.addDni(dto.getDni())
			.createPatient();
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
