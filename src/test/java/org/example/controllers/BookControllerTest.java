package org.example.controllers;

import org.example.service.BookEntityService;
import org.example.service.DTO.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookEntityService bookService;

    @InjectMocks
    private BookController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetBookById() {
        UUID id = UUID.randomUUID();
        BookDTO dto = new BookDTO();
        when(bookService.getBookById(id)).thenReturn(dto);

        ResponseEntity<BookDTO> response = controller.getBookById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testGetBookByIdThrowsException() {
        UUID id = UUID.randomUUID();

        when(bookService.getBookById(id)).thenThrow(new RuntimeException());

        ResponseEntity<BookDTO> response = controller.getBookById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllBooks() {
        BookDTO dto = new BookDTO();
        when(bookService.getAllBooks()).thenReturn( Collections.singletonList(dto));

        ResponseEntity<List<BookDTO>> response = controller.getAllBooks();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(dto, response.getBody().get(0));
    }

    @Test
    void testCreateBook() {
        BookDTO dtoToCreate = new BookDTO();
        BookDTO createdDto = new BookDTO();

        when(bookService.createOrUpdateBook(dtoToCreate)).thenReturn(createdDto);

        ResponseEntity<BookDTO> response = controller.createBook(dtoToCreate);

        assertEquals( HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
    }

    @Test
    void testUpdateBook() {
        UUID id = UUID.randomUUID();
        BookDTO dto = new BookDTO();
        dto.setUuid(id);
        when(bookService.createOrUpdateBook(dto)).thenReturn(dto);

        ResponseEntity<BookDTO> response = controller.updateBook(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testDeleteBook() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> response = controller.deleteBook(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(bookService, times(1)).deleteBook(id);
    }
}
