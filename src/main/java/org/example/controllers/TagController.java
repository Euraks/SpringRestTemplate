package org.example.controllers;

import org.example.service.DTO.TagDTO;
import org.example.service.TagEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagEntityService tagService;

    @Autowired
    public TagController(TagEntityService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDTO> getTagById(@PathVariable UUID id) {
        try {
            TagDTO dto = tagService.getTagById(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<List<TagDTO>> getAllTags() {
        List<TagDTO> dtos = tagService.getAllTags();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<TagDTO> createTag(@RequestBody TagDTO dto) {
        TagDTO createdDto = tagService.saveTag(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagDTO> updateTag(@PathVariable UUID id, @RequestBody TagDTO dto) {
        dto.setUuid(id);
        TagDTO updatedDto = tagService.saveTag(dto);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable UUID id) {
        tagService.deleteTag(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
