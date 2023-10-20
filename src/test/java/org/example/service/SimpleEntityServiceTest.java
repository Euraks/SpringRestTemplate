package org.example.service;

import static org.junit.jupiter.api.Assertions.*;

import org.example.model.entity.SimpleEntity;
import org.example.repository.SimpleEntityRepository;
import org.example.service.DTO.SimpleEntityDTO;
import org.example.service.DTO.mapper.SimpleEntityMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Optional;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class SimpleEntityServiceTest {

    @Mock
    private SimpleEntityRepository repository;

    @Mock
    private SimpleEntityMapper mapper;

    @InjectMocks
    private SimpleEntityService service;

    private UUID testUuid;
    private SimpleEntity testEntity;
    private SimpleEntityDTO testDTO;

    @BeforeEach
    public void setUp() {
        testUuid = UUID.randomUUID();
        testEntity = new SimpleEntity();
        testDTO = new SimpleEntityDTO();
        testDTO.setUuid(testUuid);
        testDTO.setDescription("Test Description");
    }

    @Test
    public void getEntityByIdShouldReturnEntity() {
        when(repository.findById(testUuid)).thenReturn(Optional.of(testEntity));
        when(mapper.toDTO(testEntity)).thenReturn(testDTO);

        SimpleEntityDTO result = service.getEntityById(testUuid);

        assertEquals(testDTO, result);
    }

    @Test
    public void getEntityByIdShouldThrowExceptionWhenNotFound() {
        when(repository.findById(testUuid)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getEntityById(testUuid));
    }

    @Test
    public void getAllEntitiesShouldReturnListOfEntities() {
        when(repository.findAll()).thenReturn(Collections.singletonList(testEntity));
        when(mapper.toDTO(testEntity)).thenReturn(testDTO);

        List<SimpleEntityDTO> result = service.getAllEntities();

        assertEquals(1, result.size());
        assertEquals(testDTO, result.get(0));
    }

    @Test
    public void saveEntityShouldReturnSavedEntity() {
        when(mapper.toEntity(testDTO)).thenReturn(testEntity);
        when(repository.save(testEntity)).thenReturn(testEntity);
        when(mapper.toDTO(testEntity)).thenReturn(testDTO);

        SimpleEntityDTO result = service.saveEntity(testDTO);

        assertEquals(testDTO, result);
    }

    @Test
    public void deleteEntityShouldCallDeleteById() {
        service.deleteEntity(testUuid);
        verify(repository).deleteById(testUuid);
    }
}
