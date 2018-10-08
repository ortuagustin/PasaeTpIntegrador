package ar.edu.unlp.pasae.tp_integrador.transformers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeValueRequestDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.CustomUserDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeValueDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Patient;
import ar.edu.unlp.pasae.tp_integrador.entities.Patient.PatientBuilder;

@Service
public class PatientTransformer extends BaseTransformer<Patient, PatientDTO> {
	@Autowired
	private PhenotypeTransformer phenotypeTransformer;

	@Autowired
	private CustomUserTransformer customUserTransformer;

	@Override
	public Patient toEntity(PatientDTO dto) {
		final PatientBuilder builder = Patient.builder();

		return builder
			.addName(dto.getName())
			.addSurname(dto.getSurname())
			.addEmail(dto.getEmail())
			.addDni(dto.getDni())
			.createPatient();
	}

	@Override
	public PatientDTO toDTO(Patient entity) {
		Set<NumericPhenotypeValueDTO> numericPhenotypes = this.getPhenotypeTransformer()
				.numericValuedToDtos(entity.getNumericPhenotypes());

		Set<CategoricPhenotypeValueRequestDTO> categoricPhenotypes = this.getPhenotypeTransformer()
				.categoricValuedToDtos(entity.getCategoricPhenotypes());

		CustomUserDTO user = this.getCustomUserTransformer().toDTO(entity.getUser());

		PatientDTO dto = new PatientDTO(entity.getId(), entity.getName(), entity.getSurname(), entity.getDni(),
				entity.getEmail(), user);

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

	/**
	 * @return the customUserTransformer
	 */
	public CustomUserTransformer getCustomUserTransformer() {
		return customUserTransformer;
	}

	/**
	 * @param customUserTransformer the customUserTransformer to set
	 */
	public void setCustomUserTransformer(CustomUserTransformer customUserTransformer) {
		this.customUserTransformer = customUserTransformer;
	}
}
