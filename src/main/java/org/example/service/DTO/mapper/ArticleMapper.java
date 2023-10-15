package org.example.service.DTO.mapper;

import org.example.model.entity.Article;
import org.example.service.DTO.ArticleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    ArticleDTO toDTO(Article entity);

    Article toEntity(ArticleDTO dto);

    @Named("toDTOList")
    List<ArticleDTO> toDTOList(List<Article> entities);

    @Named("toEntityList")
    List<Article> toEntityList(List<ArticleDTO> dtos);
}
