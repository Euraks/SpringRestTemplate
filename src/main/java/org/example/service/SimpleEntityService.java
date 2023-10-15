package org.example.service;

import org.example.model.entity.SimpleEntity;
import org.example.repository.SimpleEntityRepository;
import org.example.service.DTO.SimpleEntityDTO;
import org.example.service.DTO.mapper.SimpleEntityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SimpleEntityService {

    private final SimpleEntityRepository repository;
    private final SimpleEntityMapper mapper;

    @Autowired
    public SimpleEntityService(SimpleEntityRepository repository, SimpleEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public SimpleEntityDTO getEntityById(UUID id) {
        SimpleEntity entity = repository.findById(id).orElseThrow(() -> new RuntimeException("Entity not found"));
        return mapper.toDTO(entity);
    }

    public List<SimpleEntityDTO> getAllEntities() {
        List<SimpleEntity> entities = repository.findAll();
        return entities.stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    public SimpleEntityDTO saveEntity(SimpleEntityDTO dto) {
        SimpleEntity entity = mapper.toEntity(dto);
        SimpleEntity savedEntity = repository.save(entity);
        return mapper.toDTO(savedEntity);
    }

    public void deleteEntity(UUID id) {
        repository.deleteById(id);
    }

}
