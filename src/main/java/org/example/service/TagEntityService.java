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
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TagEntityService {

    private final TagEntityRepository tagRepository;
    private final TagMapper tagMapper;
    private final BookEntityRepository bookRepository;
    private final BookMapper bookMapper;

    @Autowired
    public TagEntityService(TagEntityRepository tagRepository, TagMapper tagMapper, BookEntityRepository bookRepository, BookMapper bookMapper) {
        this.tagRepository = tagRepository;
        this.tagMapper = tagMapper;
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
    }

    @Transactional
    public TagDTO getTagById(UUID id) {
        Tag tag = tagRepository.findById( id )
                .orElseThrow( () -> new RuntimeException( "Tag not found" ) );

        TagDTO dto = tagMapper.toDTO( tag );
//        dto.setBookList( tag.getBookEntities().stream().map( bookMapper::toEntity ).collect( Collectors.toList() ) );

        return dto;
    }


    @Transactional
    public List<TagDTO> getAllTags() {
        List<Tag> tagList = tagRepository.findAll();
        return tagMapper.toDTOList(tagList);
    }

    @Transactional
    public List<Book> getBooksByTag(UUID tagId) {
        Optional<Tag> tagOpt = tagRepository.findById(tagId);
        if (tagOpt.isPresent()) {
            Tag tag = tagOpt.get();
            List<Book> books = tag.getBookEntities(); // Предполагая, что у вас есть такой метод в классе Tag
            return books;
        }
        return Collections.emptyList();
    }

    @Transactional
    public TagDTO saveTag(TagDTO dto) {
        Tag tag;
        if (dto.getUuid() != null && tagRepository.existsById(dto.getUuid())) {
            tag = tagRepository.findById(dto.getUuid()).get();
            tag.setTagName(dto.getTagName());
        } else {
            tag = tagMapper.toEntity(dto);
        }

        tag = tagRepository.save( tag );

        if (dto.getBookEntities() != null && !dto.getBookEntities().isEmpty()) {
            List<BookDTO> bookDTOList = dto.getBookEntities();
            for (BookDTO bookDTO : bookDTOList) {
                Book book;
                if (bookDTO.getUuid() != null) {
                    Optional<Book> existingTag = bookRepository.findById(bookDTO.getUuid());
                    if (existingTag.isPresent()) {
                        book = existingTag.get();
                    } else {
                        book = bookMapper.toEntity(bookDTO);
                        book = bookRepository.save(book);
                    }
                } else {
                    book = bookMapper.toEntity(bookDTO);
                    book = bookRepository.save(book);
                }
                book.addTag(tag);
            }
        }

        tag = tagRepository.save( tag );

        TagDTO savedDto = tagMapper.toDTO( tag );
//        savedDto.setBookIds( tag.getBookEntities().stream().map( Book::getUuid ).collect( Collectors.toList() ) );
        return savedDto;
    }


    @Transactional
    public void deleteTag(UUID id) {
        tagRepository.deleteById( id );
    }
}
