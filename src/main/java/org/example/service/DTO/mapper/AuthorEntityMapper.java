package org.example.service.DTO.mapper;

import org.example.model.entity.AuthorEntity;
import org.example.service.DTO.AuthorEntityDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = ArticleMapper.class)
public interface AuthorEntityMapper {

    @Mappings({
            @Mapping(source = "authorName", target = "authorName"),
            @Mapping(source = "uuid", target = "uuid"),
            @Mapping(source = "articleList", target = "articleList", qualifiedByName = "toDTOList")
    })
    AuthorEntityDTO toDTO(AuthorEntity entity);

    @Mappings({
            @Mapping(source = "authorName", target = "authorName"),
            @Mapping(source = "uuid", target = "uuid"),
            @Mapping(source = "articleList", target = "articleList", qualifiedByName = "toEntityList")
    })
    AuthorEntity toEntity(AuthorEntityDTO dto);

    List<AuthorEntityDTO> toDTOList(List<AuthorEntity> entities);
    List<AuthorEntity> toEntityList(List<AuthorEntityDTO> dtos);
}
