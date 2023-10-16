package org.example.service.DTO.mapper;

import org.example.model.entity.Book;
import org.example.model.entity.Tag;
import org.example.service.DTO.TagDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = BookMapper.class)
public interface TagMapper {
    @Mappings({
            @Mapping(source = "tagName", target = "tagName"),
            @Mapping(source = "uuid", target = "uuid"),
            @Mapping(source = "bookEntities", target = "bookIds", qualifiedByName = "toUUIDList")
    })
    TagDTO toDTO(Tag entity);

    @Mappings({
            @Mapping(source = "tagName", target = "tagName"),
            @Mapping(source = "uuid", target = "uuid"),
            @Mapping(source = "bookIds", target = "bookEntities", qualifiedByName = "toEntityList")
    })
    Tag toEntity(TagDTO dto);

    List<TagDTO> toDTOList(List<Tag> entities);

    List<Tag> toEntityList(List<TagDTO> dtos);

    default UUID mapBookToUUID(Book book) {
        return book.getUuid();
    }

    default Book mapUUIDToBook(UUID uuid) {
        Book book = new Book();
        book.setUuid(uuid);
        return book;
    }
}