package org.example.service;


import jakarta.persistence.EntityNotFoundException;
import org.example.model.entity.Book;
import org.example.repository.BookEntityRepository;
import org.example.service.DTO.BookDTO;
import org.example.service.DTO.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
public class BookEntityService {

    private final BookEntityRepository bookRepository;

    private final BookMapper bookMapper;

    @Autowired
    public BookEntityService(BookEntityRepository bookRepository, BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public BookDTO getBookById(UUID id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return bookMapper.toDTO(book);
    }

    @Transactional
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(bookMapper::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public BookDTO saveOrUpdateBook(BookDTO dto) {
        Optional<Book> existingBookOpt = bookRepository.findById(dto.getUuid());

        if (existingBookOpt.isPresent()) {
            Book existingBook = existingBookOpt.get();
            Book updatedBook = bookMapper.toEntity(dto);
            existingBook.setBookText(updatedBook.getBookText());
            existingBook.setTagEntities(updatedBook.getTagEntities());
            return bookMapper.toDTO(bookRepository.save(existingBook));
        } else {
            Book newBook = bookMapper.toEntity(dto);
            return bookMapper.toDTO(bookRepository.save(newBook));
        }
    }

    @Transactional
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
}
