package ar.edu.unlp.pasae.tp_integrador.transformers;

import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.PatientDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Patient;

@Service
public class PatientTransformer implements Transformer<Patient, PatientDTO> {
	@Override
	public Patient toEntity(PatientDTO dto) {
		return new Patient(dto.getId(), dto.getName(), dto.getSurname(), dto.getDni(), dto.getEmail());
	}

	@Override
	public PatientDTO toDTO(Patient entity) {
		return new PatientDTO(entity.getId(), entity.getName(), entity.getSurname(), entity.getDni(), entity.getEmail());
	}
}
