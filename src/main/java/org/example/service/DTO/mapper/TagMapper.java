package org.example.service.DTO.mapper;

import org.example.model.entity.Tag;
import org.example.service.DTO.TagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
@Mapper(componentModel = "spring")
public interface TagMapper {
    @Mappings({
            @Mapping(source = "tagName", target = "tagName"),
            @Mapping(source = "uuid", target = "uuid"),
            @Mapping(target = "bookEntities", ignore = true)  // Ignore the bookEntities field
    })
    TagDTO toDTO(Tag entity);

    @Mappings({
            @Mapping(source = "tagName", target = "tagName"),
            @Mapping(source = "uuid", target = "uuid"),
    })
    Tag toEntity(TagDTO dto);

    List<TagDTO> toDTOList(List<Tag> entities);
    List<Tag> toEntityList(List<TagDTO> dtos);
}