package ar.edu.unlp.pasae.tp_integrador.transformers;

import java.util.Collection;
import java.util.stream.Collectors;

public abstract class BaseTransformer<Entity, DTO> implements Transformer<Entity, DTO> {
  @Override
  public abstract Entity toEntity(DTO dto);

  @Override
  public abstract DTO toDTO(Entity entity);

  @Override
  public Collection<Entity> manyToEntity(Collection<DTO> dtos) {
    return dtos.stream().map((dto) -> this.toEntity(dto)).collect(Collectors.toList());
  }

  @Override
  public Collection<DTO> manyToDto(Collection<Entity> entities) {
    return entities.stream().map((entity) -> this.toDTO(entity)).collect(Collectors.toList());
  }
}