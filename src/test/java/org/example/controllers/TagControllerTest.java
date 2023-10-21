package org.example.controllers;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.example.service.DTO.TagDTO;
import org.example.service.TagEntityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

class TagControllerTest {

    @Mock
    private TagEntityService tagService;

    @InjectMocks
    private TagController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTagById() {
        UUID id = UUID.randomUUID();
        TagDTO dto = new TagDTO();
        when(tagService.getTagById(id)).thenReturn(dto);

        ResponseEntity<TagDTO> response = controller.getTagById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testGetTagByIdThrowsException() {
        UUID id = UUID.randomUUID();

        when(tagService.getTagById(id)).thenThrow(new RuntimeException());

        ResponseEntity<TagDTO> response = controller.getTagById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testGetAllTags() {
        TagDTO dto = new TagDTO();
        when(tagService.getAllTags()).thenReturn(Collections.singletonList(dto));

        ResponseEntity<List<TagDTO>> response = controller.getAllTags();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull( response.getBody() ).size());
        assertEquals(dto, response.getBody().get(0));
    }

    @Test
    void testCreateTag() {
        TagDTO dtoToCreate = new TagDTO();
        TagDTO createdDto = new TagDTO();

        when(tagService.saveTag(dtoToCreate)).thenReturn(createdDto);

        ResponseEntity<TagDTO> response = controller.createTag(dtoToCreate);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
    }

    @Test
    void testUpdateTag() {
        UUID id = UUID.randomUUID();
        TagDTO dto = new TagDTO();
        dto.setUuid(id);
        when(tagService.saveTag(dto)).thenReturn(dto);

        ResponseEntity<TagDTO> response = controller.updateTag(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(dto, response.getBody());
    }

    @Test
    void testDeleteTag() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> response = controller.deleteTag(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tagService, times(1)).deleteTag(id);
    }
}
