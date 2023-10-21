package org.example.controllers;

import org.example.service.AuthorEntityService;
import org.example.service.DTO.ArticleDTO;
import org.example.service.DTO.AuthorEntityDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

class AuthorEntityControllerTest {

    @Mock
    private AuthorEntityService service;

    @InjectMocks
    private AuthorEntityController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAuthorById() {
        UUID id = UUID.randomUUID();
        AuthorEntityDTO dto = new AuthorEntityDTO();
        when(service.getAuthorById(id)).thenReturn(dto);

        ResponseEntity<AuthorEntityDTO> response = controller.getAuthorById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testGetAuthorByIdThrowsException() {
        UUID id = UUID.randomUUID();

        when(service.getAuthorById(id)).thenThrow(new RuntimeException());

        ResponseEntity<AuthorEntityDTO> response = controller.getAuthorById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetArticlesByAuthorId() {
        UUID id = UUID.randomUUID();
        AuthorEntityDTO dto = new AuthorEntityDTO();
        dto.setArticleList(new ArrayList<>());
        when(service.getAuthorById(id)).thenReturn(dto);

        ResponseEntity<List<ArticleDTO>> response = controller.getArticlesByAuthorId(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto.getArticleList(), response.getBody());
    }

    @Test
    void testGetAllAuthors() {
        AuthorEntityDTO dto = new AuthorEntityDTO();
        when(service.getAllAuthors()).thenReturn( Collections.singletonList(dto));

        ResponseEntity<List<AuthorEntityDTO>> response = controller.getAllAuthors();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(dto, response.getBody().get(0));
    }

    @Test
    void testCreateAuthor() {
        AuthorEntityDTO dtoToCreate = new AuthorEntityDTO();
        AuthorEntityDTO createdDto = new AuthorEntityDTO();

        when(service.saveAuthor(dtoToCreate)).thenReturn(createdDto);

        ResponseEntity<AuthorEntityDTO> response = controller.createAuthor(dtoToCreate);

        assertEquals( HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
    }

    @Test
    void testUpdateAuthor() {
        UUID id = UUID.randomUUID();
        AuthorEntityDTO dto = new AuthorEntityDTO();
        dto.setUuid(id);
        when(service.saveAuthor(dto)).thenReturn(dto);

        ResponseEntity<AuthorEntityDTO> response = controller.updateAuthor(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testDeleteAuthor() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> response = controller.deleteAuthor(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteAuthor(id);
    }
}
