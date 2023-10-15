package org.example.controllers;
import org.example.service.AuthorEntityService;
import org.example.service.DTO.ArticleDTO;
import org.example.service.DTO.AuthorEntityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/authors")
public class AuthorEntityController {

    private final AuthorEntityService service;

    @Autowired
    public AuthorEntityController(AuthorEntityService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorEntityDTO> getAuthorById(@PathVariable UUID id) {
        try {
            AuthorEntityDTO dto = service.getAuthorById(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/articles")
    public ResponseEntity<List<ArticleDTO>> getArticlesByAuthorId(@PathVariable UUID id) {
        try {
            AuthorEntityDTO dto = service.getAuthorById(id);
            return new ResponseEntity<>(dto.getArticleList(), HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<List<AuthorEntityDTO>> getAllAuthors() {
        List<AuthorEntityDTO> dtos = service.getAllAuthors();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<AuthorEntityDTO> createAuthor(@RequestBody AuthorEntityDTO dto) {
        AuthorEntityDTO createdDto = service.saveAuthor(dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AuthorEntityDTO> updateAuthor(@PathVariable UUID id, @RequestBody AuthorEntityDTO dto) {
        dto.setUuid(id);
        AuthorEntityDTO updatedDto = service.saveAuthor(dto);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable UUID id) {
        service.deleteAuthor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
