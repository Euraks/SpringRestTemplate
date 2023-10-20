package org.example.service.DTO.mapper;

import org.example.model.entity.SimpleEntity;
import org.example.service.DTO.SimpleEntityDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class SimpleEntityMapperTest {

    private final SimpleEntityMapper simpleEntityMapper = Mappers.getMapper( SimpleEntityMapper.class );

    @Test
    void testToDTO() {
        SimpleEntity entity = new SimpleEntity();

        SimpleEntityDTO result = simpleEntityMapper.toDTO( entity );

    }

    @Test
    void testToEntity() {
        SimpleEntityDTO dto = new SimpleEntityDTO();

        SimpleEntity result = simpleEntityMapper.toEntity( dto );

    }
}
