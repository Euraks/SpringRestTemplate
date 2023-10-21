package org.example.service;


import org.example.model.entity.Book;
import org.example.repository.BookEntityRepository;
import org.example.repository.TagEntityRepository;
import org.example.service.DTO.BookDTO;
import org.example.service.DTO.TagDTO;
import org.example.service.DTO.mapper.BookMapper;
import org.example.service.DTO.mapper.TagMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.example.model.entity.Tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BookEntityServiceTest {

    @InjectMocks
    private BookEntityService bookEntityService;

    @Mock
    private BookEntityRepository bookRepository;

    @Mock
    private TagEntityRepository tagRepository;

    @Mock
    private BookMapper bookMapper;

    @Mock
    private TagMapper tagMapper;

    private UUID testUUID;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        testUUID = UUID.randomUUID();
    }

    @Test
    void createOrUpdateBookNewBookTest() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setTagEntities(new ArrayList<>());
        TagDTO tagDTO = new TagDTO();

        Book book = new Book();
        Tag tag = new Tag();

        when(tagMapper.toEntity(tagDTO)).thenReturn(tag);
        when(bookMapper.toEntity(bookDTO)).thenReturn(book);
        when(tagRepository.save(tag)).thenReturn(tag);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDTO(book)).thenReturn(bookDTO);


        BookDTO result = bookEntityService.createOrUpdateBook(bookDTO);

        assertNotNull(result);
    }


    @Test
    void getAllBooksTest() {
        Book book = new Book();
        List<Book> books = Collections.singletonList(book);

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.toDTOList(books)).thenReturn(Collections.singletonList(new BookDTO()));

        List<BookDTO> results = bookEntityService.getAllBooks();

        assertFalse(results.isEmpty());
    }

    @Test
    void getBookByIdTest() {
        Book book = new Book();

        when(bookRepository.findById(testUUID)).thenReturn( Optional.of(book));
        when(bookMapper.toDTO(book)).thenReturn(new BookDTO());

        BookDTO result = bookEntityService.getBookById(testUUID);

        assertNotNull(result);
    }

    @Test
    void getBookByIdNotFoundTest() {
        when(bookRepository.findById(testUUID)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookEntityService.getBookById(testUUID));
    }

    @Test
    void deleteBookTest() {
        doNothing().when(bookRepository).deleteById(testUUID);

        bookEntityService.deleteBook(testUUID);

        verify(bookRepository, times(1)).deleteById(testUUID);
    }
    @Test
    void createOrUpdateBookNewTagWithNullUUIDTest() {
        BookDTO bookDTO = new BookDTO();
        TagDTO tagDTO = new TagDTO();
        bookDTO.setTagEntities(Collections.singletonList(tagDTO));

        Tag tag = new Tag();
        Book book = new Book();

        when(tagMapper.toEntity(tagDTO)).thenReturn(tag);
        when(tagRepository.save(tag)).thenReturn(tag);
        when(bookMapper.toEntity(bookDTO)).thenReturn(book);

        bookEntityService.createOrUpdateBook(bookDTO);

        verify(tagMapper).toEntity(tagDTO);
        verify(tagRepository).save(tag);
    }

    @Test
    void createOrUpdateBookExistingTagWithUUIDTest() {
        BookDTO bookDTO = new BookDTO();
        TagDTO tagDTO = new TagDTO();
        tagDTO.setUuid(testUUID);
        tagDTO.setTagName("UpdatedTagName");
        bookDTO.setTagEntities(Collections.singletonList(tagDTO));

        Tag existingTag = new Tag();
        Book book = new Book();

        when(tagRepository.findById(testUUID)).thenReturn(Optional.of(existingTag));
        when(tagRepository.save(existingTag)).thenReturn(existingTag);
        when(bookMapper.toEntity(bookDTO)).thenReturn(book); // добавили эту строку

        bookEntityService.createOrUpdateBook(bookDTO);

        assertEquals("UpdatedTagName", existingTag.getTagName());
        verify(tagRepository).save(existingTag);
    }


    @Test
    void createOrUpdateBookNonExistingTagWithUUIDTest() {
        BookDTO bookDTO = new BookDTO();
        TagDTO tagDTO = new TagDTO();
        tagDTO.setUuid(testUUID);
        bookDTO.setTagEntities(Collections.singletonList(tagDTO));

        Tag tag = new Tag();
        Book book = new Book();

        when(tagRepository.findById(testUUID)).thenReturn(Optional.empty());
        when(tagMapper.toEntity(tagDTO)).thenReturn(tag);
        when(tagRepository.save(tag)).thenReturn(tag);
        when(bookMapper.toEntity(bookDTO)).thenReturn(book);

        bookEntityService.createOrUpdateBook(bookDTO);

        verify(tagMapper).toEntity(tagDTO);
        verify(tagRepository).save(tag);
    }





    @AfterEach
    public void cleanup() {
        reset(bookRepository, tagRepository, bookMapper, tagMapper);
    }
}
