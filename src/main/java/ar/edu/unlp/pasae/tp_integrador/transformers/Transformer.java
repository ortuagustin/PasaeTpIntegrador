package ar.edu.unlp.pasae.tp_integrador.transformers;

import java.util.Collection;

public interface Transformer<Entity, DTO> {
	/**
	 * Convierte el DTO a Entidad
	 *
	 * @param dto el DTO a convertir a entidad
	 *
	 * @return Entity
	 */
	Entity toEntity(DTO dto);

	/**
	 * Convierte una coleccion de DTO en una coleccion de Entidades
	 *
	 * @param dtos coleccion de dtos a convertir
	 *
	 * @return Collection<Entity>
	 */
	Collection<Entity> manyToEntity(Collection<DTO> dtos);

	/**
	 * Convierte la Entidad en un DTO
	 *
	 * @param entity la entidad a convertir en dto
	 *
	 * @return Entity
	 */
	DTO toDTO(Entity entity);

	/**
	 * Convierte una coleccion de entidades en una coleccion de dtos
	 *
	 * @param entities coleccion de entidades a convertir
	 *
	 * @return Collection<DTO>
	 */
	Collection<DTO> manyToDto(Collection<Entity> entities);
}
