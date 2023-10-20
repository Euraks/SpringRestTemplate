package org.example.service.DTO.mapper;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.model.entity.Book;
import org.example.model.entity.Tag;
import org.example.service.DTO.BookDTO;
import org.example.service.DTO.TagDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mapstruct.factory.Mappers;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class BookMapperTest {

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private final BookMapper bookMapper = Mappers.getMapper(BookMapper.class);

    @BeforeEach
    public void setup() {
        lenient().when(tagMapper.toDTO(any())).thenReturn(new TagDTO());
        lenient().when(tagMapper.toEntity(any())).thenReturn(new Tag());
    }

    @Test
    void testToDTO() {
        Book book = new Book();
        book.setBookText("Example text");
        UUID uuid = UUID.randomUUID();
        book.setUuid(uuid);
        book.setTagEntities(Collections.singletonList(new Tag()));

        BookDTO result = bookMapper.toDTO(book);

        assertEquals("Example text", result.getBookText());
        assertEquals(uuid, result.getUuid());
        assertNotNull(result.getTagEntities());
//        assertEquals(1, result.getTagEntities().size());
    }

    @Test
    void testToEntity() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookText("Example text");
        UUID uuid = UUID.randomUUID();
        bookDTO.setUuid(uuid);

        Book result = bookMapper.toEntity(bookDTO);

        assertEquals("Example text", result.getBookText());
        assertEquals(uuid, result.getUuid());
    }

    @Test
    void testToDTOList() {
        Book book = new Book();
        book.setBookText("Example text");
        UUID uuid = UUID.randomUUID();
        book.setUuid(uuid);
        book.setTagEntities(Collections.singletonList(new Tag()));

        List<BookDTO> result = bookMapper.toDTOList(Collections.singletonList(book));

        assertEquals(1, result.size());
        BookDTO dto = result.get(0);
        assertEquals("Example text", dto.getBookText());
        assertEquals(uuid, dto.getUuid());
    }

    @Test
    void testToEntityList() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookText("Example text");
        UUID uuid = UUID.randomUUID();
        bookDTO.setUuid(uuid);

        List<Book> result = bookMapper.toEntityList(Collections.singletonList(bookDTO));

        assertEquals(1, result.size());
        Book entity = result.get(0);
        assertEquals("Example text", entity.getBookText());
        assertEquals(uuid, entity.getUuid());
    }
}
