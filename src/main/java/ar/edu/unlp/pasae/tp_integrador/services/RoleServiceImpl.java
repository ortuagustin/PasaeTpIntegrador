package ar.edu.unlp.pasae.tp_integrador.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unlp.pasae.tp_integrador.dtos.RoleDTO;
import ar.edu.unlp.pasae.tp_integrador.entities.Role;
import ar.edu.unlp.pasae.tp_integrador.repositories.RoleRepository;
import ar.edu.unlp.pasae.tp_integrador.transformers.RoleTransformer;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleRepository roleRepository; 
	@Autowired
	private RoleTransformer roleTransformer;

	/**
	 * Lista todos los roles de la DB
	 * @return La lista de roles desde la DB
	 */
	@Override
	public List<RoleDTO> listAll() {
		List<Role> roleList = this.getRoleRepository().findAll();
		return this.getRoleTransformer().transform(roleList);
	}
	
	private RoleRepository getRoleRepository() {
		return this.roleRepository;
	}
	
	private RoleTransformer getRoleTransformer() {
		return this.roleTransformer;
	}
}
