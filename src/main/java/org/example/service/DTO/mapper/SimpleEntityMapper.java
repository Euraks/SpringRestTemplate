package org.example.service.DTO.mapper;

import org.example.model.entity.SimpleEntity;
import org.example.service.DTO.SimpleEntityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SimpleEntityMapper {
    SimpleEntityDTO toDTO(SimpleEntity entity);
    SimpleEntity toEntity(SimpleEntityDTO dto);
}
