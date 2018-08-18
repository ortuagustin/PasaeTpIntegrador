package ar.edu.unlp.pasae.tp_integrador.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import ar.edu.unlp.pasae.tp_integrador.entities.CustomUser;

public interface CustomUserRepository extends PagingAndSortingRepository<CustomUser, Long> {
	Optional<CustomUser> findByUsername(String username);
	Page<CustomUser> findAll(Pageable pageRequest);
	Page<CustomUser> findByUsernameContainingIgnoreCaseOrFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String username, String firstName, String lastName, Pageable pageRequest);
}
