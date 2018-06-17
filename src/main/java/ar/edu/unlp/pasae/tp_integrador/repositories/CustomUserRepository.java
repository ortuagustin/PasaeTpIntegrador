package ar.edu.unlp.pasae.tp_integrador.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;

public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
	Optional<CustomUser> findByUsername(String username);
	List<CustomUser> findAll();
}
