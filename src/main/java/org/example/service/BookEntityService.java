package org.example.service;


import org.example.model.entity.Book;
import org.example.model.entity.Tag;
import org.example.repository.BookEntityRepository;
import org.example.repository.TagEntityRepository;
import org.example.service.DTO.BookDTO;
import org.example.service.DTO.TagDTO;
import org.example.service.DTO.mapper.BookMapper;
import org.example.service.DTO.mapper.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class BookEntityService {

    private final BookEntityRepository bookRepository;
    private final TagEntityRepository tagRepository;
    private final BookMapper bookMapper;
    private final TagMapper tagMapper;

    @Autowired
    public BookEntityService(
            BookEntityRepository bookRepository,
            TagEntityRepository tagRepository,
            BookMapper bookMapper,
            TagMapper tagMapper) {
        this.bookRepository = bookRepository;
        this.tagRepository = tagRepository;
        this.bookMapper = bookMapper;
        this.tagMapper = tagMapper;
    }

    @Transactional
    public BookDTO createOrUpdateBook(BookDTO bookDTO) {
        Set<Tag> tags = new HashSet<>();

        for (TagDTO tagDTO : bookDTO.getTagEntities()) {
            Tag tag;

            if (tagDTO.getUuid() != null) {
                tag = tagRepository.findById(tagDTO.getUuid()).orElse(null);
                if (tag != null) {
                    tag.setTagName(tagDTO.getTagName());
                } else {
                    tag = tagMapper.toEntity(tagDTO);
                }
            } else {
                tag = tagMapper.toEntity(tagDTO);
            }

            tag = tagRepository.save(tag);
            tags.add(tag);
        }

        Book book;
        if (bookDTO.getUuid() != null) {
            book = bookRepository.findById(bookDTO.getUuid()).orElse(null);
            if (book != null) {
                book.setBookText(bookDTO.getBookText());
            } else {
                book = bookMapper.toEntity(bookDTO);
                book.setUuid(UUID.randomUUID());
            }
        } else {
            book = bookMapper.toEntity(bookDTO);
            book.setUuid(UUID.randomUUID());
        }

        book.setTagEntities(new ArrayList<>(tags));
        book = bookRepository.save(book);
        return bookMapper.toDTO(book);
    }



    @Transactional
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDTO> bookDTOs = bookMapper.toDTOList(books);

        for (int i = 0; i < books.size(); i++) {
            Book book = books.get(i);
            BookDTO bookDTO = bookDTOs.get(i);
            bookDTO.setTagEntities(tagMapper.toDTOList(book.getTagEntities()));
        }

        return bookDTOs;
    }

    public BookDTO getBookById(UUID id) {
        // Fetch book from the database
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

        // Map the book entity to DTO
        BookDTO bookDTO = bookMapper.toDTO(book);

        // Map the tags without recursive book list inside them
        bookDTO.setTagEntities(tagMapper.toDTOList(book.getTagEntities()));

        return bookDTO;
    }

    @Transactional
    public void deleteBook(UUID bookId) {
        bookRepository.deleteById( bookId );
    }
}
