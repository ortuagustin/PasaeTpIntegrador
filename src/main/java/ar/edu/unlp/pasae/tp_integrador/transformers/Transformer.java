package ar.edu.unlp.pasae.tp_integrador.transformers;

public interface Transformer<Entity, DTO> {
	Entity toEntity(DTO dto);
	DTO toDTO(Entity entity);
}
