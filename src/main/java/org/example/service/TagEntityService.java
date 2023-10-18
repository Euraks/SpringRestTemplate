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
    public TagEntityService(TagEntityRepository tagRepository,
                            TagMapper tagMapper,
                            BookEntityRepository bookRepository,
                            BookMapper bookMapper) {
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
        dto.setBookEntities( bookMapper.toDTOList( getBooks( dto.getUuid() ) ) );
        return dto;
    }

    @Transactional
    public List<TagDTO> getAllTags() {
        List<Tag> tagList = tagRepository.findAll();
        List<TagDTO> tagDTOList = tagMapper.toDTOList( tagList );
        for (TagDTO t : tagDTOList) {
            t.setBookEntities( bookMapper.toDTOList( getBooks( t.getUuid() ) ) );
        }
        return tagDTOList;
    }

    @Transactional
    public List<Book> getBooksByTag(UUID tagId) {
        Optional<Tag> tagOpt = tagRepository.findById( tagId );
        return tagOpt.map( Tag::getBookEntities ).orElse( Collections.emptyList() );
    }

    @Transactional
    public TagDTO saveTag(TagDTO dto) {
        Tag tag;

        if (dto.getUuid() != null && tagRepository.existsById( dto.getUuid() )) {
            tag = tagRepository.findById( dto.getUuid() ).orElseThrow( () -> new RuntimeException( "Tag not found!" ) );
            tag.setTagName( dto.getTagName() );
        } else {
            tag = tagMapper.toEntity( dto );
        }

        tag = tagRepository.save( tag );

        if (dto.getBookEntities() != null && !dto.getBookEntities().isEmpty()) {
            for (BookDTO bookDTO : dto.getBookEntities()) {
                Book book;

                if (bookDTO.getUuid() != null && bookRepository.existsById( bookDTO.getUuid() )) {
                    book = bookRepository.findById( bookDTO.getUuid() ).orElseThrow( () -> new RuntimeException( "Book not found!" ) );
                    book.setBookText( bookDTO.getBookText() );
                } else {
                    book = bookMapper.toEntity( bookDTO );
                }

                book.addTag( tag );
                bookRepository.save( book );
            }
        }

        List<Book> bookList = getBooks( tag.getUuid() );
        TagDTO savedDto = tagMapper.toDTO( tag );
        savedDto.setBookEntities( bookMapper.toDTOList( bookList ) );
        return savedDto;
    }


    private List<Book> getBooks(UUID uuid) {
        List<Book> bookList = bookRepository.findByTagEntitiesUuid( uuid );
        for (Book b : bookList) {
            b.setTagEntities( null );
        }
        return bookList;
    }

    @Transactional
    public void deleteTag(UUID id) {
        List<Book> books = bookRepository.findByTagEntitiesUuid( id );
        for (Book book : books) {
            bookRepository.deleteById( book.getUuid() );
        }
        tagRepository.deleteById( id );

    }
}
