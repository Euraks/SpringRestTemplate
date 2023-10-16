package org.example.service.DTO.mapper;

import org.example.model.entity.Book;
import org.example.model.entity.Tag;
import org.example.service.DTO.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface BookMapper {
    @Mappings({
            @Mapping(source = "bookText", target = "bookText"),
            @Mapping(source = "uuid", target = "uuid"),
            @Mapping(source = "tagEntities", target = "tagIds", qualifiedByName = "toUUIDList")
    })
    BookDTO toDTO(Book entity);

    @Mappings({
            @Mapping(source = "bookText", target = "bookText"),
            @Mapping(source = "uuid", target = "uuid"),
            @Mapping(source = "tagIds", target = "tagEntities", qualifiedByName = "toEntityList")
    })
    Book toEntity(BookDTO dto);

    List<BookDTO> toDTOList(List<Book> entities);

    List<Book> toEntityList(List<BookDTO> dtos);

    default UUID mapTagToUUID(Tag tag) {
        return tag.getUuid();
    }

    default Tag mapUUIDToTag(UUID uuid) {
        Tag tag = new Tag();
        tag.setUuid( uuid );
        return tag;
    }
}