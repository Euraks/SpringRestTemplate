package org.example.controllers;

import org.example.service.DTO.SimpleEntityDTO;
import org.example.service.SimpleEntityService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SimpleEntityControllerTest {

    @Mock
    private SimpleEntityService service;

    @InjectMocks
    private SimpleEntityController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks( this );
    }

    @Test
    void testGetEntityById() {
        UUID id = UUID.randomUUID();
        SimpleEntityDTO dto = new SimpleEntityDTO();
        when( service.getEntityById( id ) ).thenReturn( dto );

        ResponseEntity<SimpleEntityDTO> response = controller.getEntityById( id );

        assertEquals( HttpStatus.OK, response.getStatusCode() );
        assertEquals( dto, response.getBody() );
    }

    @Test
    void testGetEntityByIdThrowsException() {
        UUID id = UUID.randomUUID();

        when(service.getEntityById(id)).thenThrow(new RuntimeException());

        ResponseEntity<SimpleEntityDTO> response = controller.getEntityById(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


    @Test
    void testGetAllEntities() {
        SimpleEntityDTO dto = new SimpleEntityDTO();
        when( service.getAllEntities() ).thenReturn( Collections.singletonList( dto ) );

        ResponseEntity<List<SimpleEntityDTO>> response = controller.getAllEntities();

        assertEquals( HttpStatus.OK, response.getStatusCode() );
        assertEquals( 1, Objects.requireNonNull( response.getBody() ).size() );
        assertEquals( dto, response.getBody().get( 0 ) );
    }

    @Test
    void testCreateEntity() {
        SimpleEntityDTO dtoToCreate = new SimpleEntityDTO();
        SimpleEntityDTO createdDto = new SimpleEntityDTO();

        when(service.saveEntity(dtoToCreate)).thenReturn(createdDto);

        ResponseEntity<SimpleEntityDTO> response = controller.createEntity(dtoToCreate);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdDto, response.getBody());
    }


    @Test
    void testUpdateEntity() {
        UUID id = UUID.randomUUID();
        SimpleEntityDTO dto = new SimpleEntityDTO();
        dto.setUuid( id );
        when( service.saveEntity( dto ) ).thenReturn( dto );

        ResponseEntity<SimpleEntityDTO> response = controller.updateEntity( id, dto );

        assertEquals( HttpStatus.OK, response.getStatusCode() );
        assertEquals( dto, response.getBody() );
    }

    @Test
    void testDeleteEntity() {
        UUID id = UUID.randomUUID();

        ResponseEntity<Void> response = controller.deleteEntity(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(service, times(1)).deleteEntity(id);
    }


}
