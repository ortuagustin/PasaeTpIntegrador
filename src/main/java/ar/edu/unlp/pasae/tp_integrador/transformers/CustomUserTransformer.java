package ar.edu.unlp.pasae.tp_integrador.transformers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.unlp.pasae.tp_integrador.dtos.CustomUserDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;

@Component
public class CustomUserTransformer implements Transformer<CustomUser, CustomUserDTO> {

	@Override
	public CustomUser toEntity(CustomUserDTO dto) {
		return new CustomUser(dto.getUsername(), dto.getPassword(), dto.getEmail(), dto.getFirstName(), dto.getLastName(), dto.getAuthorities());
	}

	@Override
	public CustomUserDTO toDTO(CustomUser entity) {
		return new CustomUserDTO(entity.getId(), entity.getUsername(), entity.getEmail(), entity.getFirstName(), entity.getLastName(), entity.getAuthorities());
	}

	public List<CustomUserDTO> transform(List<CustomUser> usersList) {
		List<CustomUserDTO> listAns = new ArrayList<CustomUserDTO>();
		for (CustomUser user : usersList) {
			listAns.add(this.toDTO(user));
		}
		return listAns;
	}

}
