package org.example.controllers;

import org.example.service.DTO.SimpleEntityDTO;
import org.example.service.SimpleEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/simples")
public class SimpleEntityController {

    private final SimpleEntityService service;

    @Autowired
    public SimpleEntityController(SimpleEntityService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SimpleEntityDTO> getEntityById(@PathVariable UUID id) {
        try {
            SimpleEntityDTO dto = service.getEntityById(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<List<SimpleEntityDTO>> getAllEntities() {
        List<SimpleEntityDTO> dtos = service.getAllEntities();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<SimpleEntityDTO> createEntity(@RequestBody SimpleEntityDTO dto) {
        SimpleEntityDTO createdDto = service.saveEntity(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SimpleEntityDTO> updateEntity(@PathVariable UUID id, @RequestBody SimpleEntityDTO dto) {
        dto.setUuid(id);
        SimpleEntityDTO updatedDto = service.saveEntity(dto);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntity(@PathVariable UUID id) {
        service.deleteEntity(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
