package org.example.service.DTO.mapper;

import javax.annotation.processing.Generated;
import org.example.model.entity.SimpleEntity;
import org.example.service.DTO.SimpleEntityDTO;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor"
)
@Component
public class SimpleEntityMapperImpl implements SimpleEntityMapper {

    @Override
    public SimpleEntityDTO toDTO(SimpleEntity entity) {
        if ( entity == null ) {
            return null;
        }

        SimpleEntityDTO simpleEntityDTO = new SimpleEntityDTO();

        simpleEntityDTO.setUuid( entity.getUuid() );
        simpleEntityDTO.setDescription( entity.getDescription() );

        return simpleEntityDTO;
    }

    @Override
    public SimpleEntity toEntity(SimpleEntityDTO dto) {
        if ( dto == null ) {
            return null;
        }

        SimpleEntity simpleEntity = new SimpleEntity();

        simpleEntity.setUuid( dto.getUuid() );
        simpleEntity.setDescription( dto.getDescription() );

        return simpleEntity;
    }
}
