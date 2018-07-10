package ar.edu.unlp.pasae.tp_integrador.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unlp.pasae.tp_integrador.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	List<Role> findAllByNameIn(List<String> list);
}
