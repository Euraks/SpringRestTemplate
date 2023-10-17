package org.example.controllers;

import org.example.service.BookEntityService;
import org.example.service.DTO.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookEntityService bookService;

    @Autowired
    public BookController(BookEntityService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable UUID id) {
        try {
            BookDTO dto = bookService.getBookById(id);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping()
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> dtos = bookService.getAllBooks();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO dto) {
        BookDTO createdDto = bookService.createOrUpdateBook( dto);
        return new ResponseEntity<>(createdDto, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBook(@PathVariable UUID id, @RequestBody BookDTO dto) {
        dto.setUuid(id);
        BookDTO updatedDto = bookService.createOrUpdateBook(dto);
        return new ResponseEntity<>(updatedDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable UUID id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
