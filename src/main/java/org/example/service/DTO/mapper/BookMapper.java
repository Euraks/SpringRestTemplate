package org.example.service.DTO.mapper;

import org.example.model.entity.Book;
import org.example.service.DTO.BookDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring", uses = TagMapper.class)
public interface BookMapper {
    @Mappings({
            @Mapping(source = "bookText", target = "bookText"),
            @Mapping(source = "uuid", target = "uuid"),
            @Mapping(source = "tagEntities", target = "tagEntities")
    })
    BookDTO toDTO(Book entity);

    @Mapping(source = "bookText", target = "bookText")
    @Mapping(source = "uuid", target = "uuid")
    Book toEntity(BookDTO dto);

    List<BookDTO> toDTOList(List<Book> entities);

    List<Book> toEntityList(List<BookDTO> dtos);
}