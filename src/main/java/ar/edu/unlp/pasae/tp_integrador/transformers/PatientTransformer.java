package ar.edu.unlp.pasae.tp_integrador.transformers;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.CategoricPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.CustomUserDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.NumericPhenotypeDTO;
import ar.edu.unlp.pasae.tp_integrador.dtos.PatientDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.CategoricPhenotypeValue;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotype;
import ar.edu.unlp.pasae.tp_integrador.entities.NumericPhenotypeValue;
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

		 return builder.addName(dto.getName())
			.addSurname(dto.getSurname())
			.addEmail(dto.getEmail())
			.addDni(dto.getDni())
			.createPatient();
	}

	@Override
	public PatientDTO toDTO(Patient entity) {
		Set<NumericPhenotypeDTO> numericPhenotypes = this.getPhenotypeTransformer()
				.numericToDtos(this.getNumericPhenotypes(entity));

		Set<CategoricPhenotypeDTO> categoricPhenotypes = this.getPhenotypeTransformer()
				.categoricToDtos(this.getCategoricPhenotypes(entity));

		CustomUserDTO user = this.getCustomUserTransformer().toDTO(entity.getUser());

		PatientDTO dto = new PatientDTO(entity.getId(), entity.getName(), entity.getSurname(), entity.getDni(),
				entity.getEmail(), user);

		dto.setCategoricPhenotypes(categoricPhenotypes);
		dto.setNumericPhenotypes(numericPhenotypes);

		return dto;
	}

	private Set<NumericPhenotype> getNumericPhenotypes(Patient entity) {
		Set<NumericPhenotype> phenotypes = new HashSet<NumericPhenotype>();

		for (NumericPhenotypeValue each : entity.getNumericPhenotypes()) {
			phenotypes.add(each.getPhenotype());
		}

		return phenotypes;
	}

	private Set<CategoricPhenotype> getCategoricPhenotypes(Patient entity) {
		Set<CategoricPhenotype> phenotypes = new HashSet<CategoricPhenotype>();

		for (CategoricPhenotypeValue each : entity.getCategoricPhenotypes()) {
			phenotypes.add(each.getPhenotype());
		}

		return phenotypes;
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
