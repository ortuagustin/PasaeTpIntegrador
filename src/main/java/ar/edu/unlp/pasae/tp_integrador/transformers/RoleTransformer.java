package ar.edu.unlp.pasae.tp_integrador.transformers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import ar.edu.unlp.pasae.tp_integrador.dtos.RoleDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Role;

@Component
public class RoleTransformer extends BaseTransformer<Role, RoleDTO> {

	@Override
	public Role toEntity(RoleDTO dto) {
		return new Role(dto.getName());
	}

	@Override
	public RoleDTO toDTO(Role entity) {
		return new RoleDTO(entity.getId(), entity.getName());
	}


	public List<RoleDTO> transform(List<Role> usersList) {
		List<RoleDTO> listAns = new ArrayList<RoleDTO>();
		for (Role user : usersList) {
			listAns.add(this.toDTO(user));
		}
		return listAns;
	}
}
