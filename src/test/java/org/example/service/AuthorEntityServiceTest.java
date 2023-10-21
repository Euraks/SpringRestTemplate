package org.example.service;

import org.example.model.entity.Article;
import org.example.model.entity.AuthorEntity;
import org.example.repository.AuthorEntityRepository;
import org.example.service.DTO.AuthorEntityDTO;
import org.example.service.DTO.mapper.AuthorEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthorEntityServiceTest {

    @InjectMocks
    private AuthorEntityService authorEntityService;

    @Mock
    private AuthorEntityRepository repository;

    @Mock
    private AuthorEntityMapper mapper;

    private UUID testUUID;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        testUUID = UUID.randomUUID();
    }

    @Test
    void testGetAuthorById() {
        AuthorEntity mockAuthor = new AuthorEntity();
        AuthorEntityDTO mockAuthorDTO = new AuthorEntityDTO();

        when(repository.findByIdWithArticles(any(UUID.class))).thenReturn(Optional.of(mockAuthor));
        when(mapper.toDTO(mockAuthor)).thenReturn(mockAuthorDTO);

        AuthorEntityDTO result = authorEntityService.getAuthorById(testUUID);

        assertEquals(mockAuthorDTO, result);
    }

    @Test
    void testGetAllAuthors() {
        AuthorEntity mockAuthor = new AuthorEntity();
        AuthorEntityDTO mockAuthorDTO = new AuthorEntityDTO();

        when(repository.findAllWithArticles()).thenReturn(Collections.singletonList(mockAuthor));
        when(mapper.toDTO(mockAuthor)).thenReturn(mockAuthorDTO);

        List<AuthorEntityDTO> results = authorEntityService.getAllAuthors();

        assertFalse(results.isEmpty());
        assertEquals(mockAuthorDTO, results.get(0));
    }

    @Test
    void testSaveAuthorNewAuthor() {
        AuthorEntityDTO mockAuthorDTO = new AuthorEntityDTO();
        AuthorEntity mockAuthorEntity = new AuthorEntity();
        when(mapper.toEntity(mockAuthorDTO)).thenReturn(mockAuthorEntity);
        when(repository.existsById(any())).thenReturn(false);
        when(repository.save(mockAuthorEntity)).thenReturn(mockAuthorEntity);
        when(mapper.toDTO(mockAuthorEntity)).thenReturn(mockAuthorDTO);

        AuthorEntityDTO result = authorEntityService.saveAuthor(mockAuthorDTO);

        assertEquals(mockAuthorDTO, result);
    }

    @Test
    void testSaveAuthorExistingAuthorWithArticlesUpdate() {
        AuthorEntityDTO mockAuthorDTO = new AuthorEntityDTO();
        AuthorEntity existingAuthorEntity = new AuthorEntity();
        Article existingArticle = new Article();
        existingArticle.setUuid(UUID.randomUUID());
        existingAuthorEntity.setArticleList(Collections.singletonList(existingArticle));

        Article updatingArticle = new Article();
        updatingArticle.setUuid(existingArticle.getUuid());
        updatingArticle.setText("Updated text");

        AuthorEntity updatingAuthorEntity = new AuthorEntity();
        updatingAuthorEntity.setUuid(testUUID);
        updatingAuthorEntity.setAuthorName("Updated Author Name");
        updatingAuthorEntity.setArticleList(Collections.singletonList(updatingArticle));

        when(mapper.toEntity(mockAuthorDTO)).thenReturn(updatingAuthorEntity);
        when(repository.existsById(testUUID)).thenReturn(true);
        when(repository.findByIdWithArticles(testUUID)).thenReturn(Optional.of(existingAuthorEntity));
        when(repository.save(existingAuthorEntity)).thenReturn(existingAuthorEntity);
        when(mapper.toDTO(existingAuthorEntity)).thenReturn(mockAuthorDTO);

        AuthorEntityDTO result = authorEntityService.saveAuthor(mockAuthorDTO);

        assertEquals(mockAuthorDTO, result);
        assertEquals("Updated Author Name", existingAuthorEntity.getAuthorName());
        assertEquals("Updated text", existingArticle.getText());
    }

    @Test
    void testSaveAuthorNewAuthorWithArticles() {
        AuthorEntityDTO mockAuthorDTO = new AuthorEntityDTO();
        Article mockArticle = new Article();
        AuthorEntity mockAuthorEntity = new AuthorEntity();
        mockAuthorEntity.setArticleList(Collections.singletonList(mockArticle));

        when(mapper.toEntity(mockAuthorDTO)).thenReturn(mockAuthorEntity);
        when(repository.existsById(any())).thenReturn(false);
        when(repository.save(mockAuthorEntity)).thenReturn(mockAuthorEntity);
        when(mapper.toDTO(mockAuthorEntity)).thenReturn(mockAuthorDTO);

        AuthorEntityDTO result = authorEntityService.saveAuthor(mockAuthorDTO);

        assertEquals(mockAuthorDTO, result);
        assertNotNull(mockArticle.getAuthor());
    }



    @Test
    void testDeleteAuthor() {
        doNothing().when(repository).deleteById(any(UUID.class));
        authorEntityService.deleteAuthor(testUUID);

        verify(repository, times(1)).deleteById(testUUID);
    }
}
