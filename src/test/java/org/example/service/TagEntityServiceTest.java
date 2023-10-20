package org.example.service;

import org.example.model.entity.Book;
import org.example.model.entity.Tag;
import org.example.repository.BookEntityRepository;
import org.example.repository.TagEntityRepository;
import org.example.service.DTO.BookDTO;
import org.example.service.DTO.TagDTO;
import org.example.service.DTO.mapper.BookMapper;
import org.example.service.DTO.mapper.TagMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagEntityServiceTest {

    @InjectMocks
    private TagEntityService tagEntityService;

    @Mock
    private TagEntityRepository tagRepository;

    @Mock
    private BookEntityRepository bookRepository;

    @Mock
    private TagMapper tagMapper;

    @Mock
    private BookMapper bookMapper;

    private UUID testUUID;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks( this );
        testUUID = UUID.randomUUID();
    }

    @Test
    public void getTagById() {
        Tag mockTag = new Tag();
        TagDTO mockTagDTO = new TagDTO();

        when( tagRepository.findById( any( UUID.class ) ) ).thenReturn( Optional.of( mockTag ) );
        when( tagMapper.toDTO( mockTag ) ).thenReturn( mockTagDTO );

        TagDTO result = tagEntityService.getTagById( testUUID );

        assertEquals( mockTagDTO, result );
    }

    @Test
    void saveTagNewTagWithoutBooks() {
        TagDTO inputDto = new TagDTO();
        inputDto.setTagName( "Test Tag" );

        Tag expectedSavedTag = new Tag();
        expectedSavedTag.setTagName( "Test Tag" );

        when( tagRepository.existsById( any() ) ).thenReturn( false );
        when( tagMapper.toEntity( inputDto ) ).thenReturn( expectedSavedTag );
        when( tagRepository.save( expectedSavedTag ) ).thenReturn( expectedSavedTag );
        when( tagMapper.toDTO( expectedSavedTag ) ).thenReturn( inputDto );

        TagDTO resultDto = tagEntityService.saveTag( inputDto );

        assertEquals( inputDto.getTagName(), resultDto.getTagName() );
        assertEquals( 0, resultDto.getBookEntities().size() );
    }


    @Test
    public void throwExceptionWhenUpdatingNonExistingTagTest() {
        TagDTO mockTagDTO = new TagDTO();
        mockTagDTO.setUuid( testUUID );

        when( tagRepository.existsById( testUUID ) ).thenReturn( false );

        assertThrows( RuntimeException.class, () -> tagEntityService.saveTag( mockTagDTO ) );
    }


    @Test
    public void getAllTagsTest() {
        Tag mockTag = new Tag();
        Book mockBook = new Book();

        when( tagRepository.findAll() ).thenReturn( Collections.singletonList( mockTag ) );

        TagDTO mockTagDTO = new TagDTO();
        when( tagMapper.toDTOList( Collections.singletonList( mockTag ) ) ).thenReturn( Collections.singletonList( mockTagDTO ) );

        when( bookRepository.findByTagEntitiesUuid( any( UUID.class ) ) ).thenReturn( Collections.singletonList( mockBook ) );

        BookDTO mockBookDTO = new BookDTO();
        when( bookMapper.toDTOList( Collections.singletonList( mockBook ) ) ).thenReturn( Collections.singletonList( mockBookDTO ) );

        List<TagDTO> result = tagEntityService.getAllTags();

        assertFalse( result.isEmpty() );
        assertEquals( 1, result.size() );
        assertEquals( mockTagDTO, result.get( 0 ) );
    }


    @Test
    public void saveTagNewTag() {
        TagDTO mockTagDTO = new TagDTO();
        Tag mockTag = new Tag();

        when( tagMapper.toEntity( mockTagDTO ) ).thenReturn( mockTag );
        when( tagRepository.existsById( any() ) ).thenReturn( false );
        when( tagRepository.save( mockTag ) ).thenReturn( mockTag );
        when( tagMapper.toDTO( mockTag ) ).thenReturn( mockTagDTO );

        TagDTO result = tagEntityService.saveTag( mockTagDTO );

        assertEquals( mockTagDTO, result );
    }

    @Test
    void saveTagNewTagWithNewBook() {
        TagDTO inputDto = new TagDTO();
        inputDto.setTagName( "Test Tag" );

        BookDTO bookDto = new BookDTO();
        bookDto.setBookText( "Test Book" );
        inputDto.setBookEntities( Collections.singletonList( bookDto ) );

        Tag mockTag = new Tag();
        mockTag.setTagName( "Test Tag" );
        Book mockBook = new Book();
        mockBook.setBookText( "Test Book" );

        when( tagRepository.existsById( any() ) ).thenReturn( false );
        when( tagMapper.toEntity( inputDto ) ).thenReturn( mockTag );
        when( tagRepository.save( mockTag ) ).thenReturn( mockTag );
        when( tagMapper.toDTO( mockTag ) ).thenReturn( inputDto );

        when( bookRepository.existsById( any() ) ).thenReturn( false );
        when( bookMapper.toEntity( bookDto ) ).thenReturn( mockBook );
        when( bookRepository.save( mockBook ) ).thenReturn( mockBook );

        TagDTO resultDto = tagEntityService.saveTag( inputDto );

        assertEquals( inputDto.getTagName(), resultDto.getTagName() );
        assertNotNull( resultDto.getBookEntities() );
        assertEquals( 0, resultDto.getBookEntities().size() );
    }


    @Test
    public void deleteTagTest() {
        Book mockBook1 = new Book();
        Book mockBook2 = new Book();
        List<Book> mockBooks = Arrays.asList( mockBook1, mockBook2 );

        when( bookRepository.findByTagEntitiesUuid( testUUID ) ).thenReturn( mockBooks );

        tagEntityService.deleteTag( testUUID );

        verify( tagRepository, times( 1 ) ).deleteById( testUUID );
    }

    @Test
    public void saveTagUpdateExistingTag() {

        TagDTO inputDto = new TagDTO();
        inputDto.setUuid( testUUID );
        inputDto.setTagName( "Updated Tag" );

        Tag existingTag = new Tag();
        existingTag.setUuid( testUUID );
        existingTag.setTagName( "Original Tag" );

        when(tagRepository.existsById(any(UUID.class))).thenReturn(true);
        when(tagRepository.findById(any(UUID.class))).thenReturn(Optional.of(existingTag));
        when(tagRepository.save(any(Tag.class))).thenReturn(existingTag);
        when(tagMapper.toDTO(any(Tag.class))).thenReturn(inputDto);

        TagDTO resultDto = tagEntityService.saveTag( inputDto );

        assertEquals( inputDto.getTagName(), resultDto.getTagName() );
    }


    @Test
    public void saveTagTagNotFound() {

        TagDTO inputDto = new TagDTO();
        inputDto.setUuid( testUUID );
        inputDto.setTagName( "Updated Tag" );


        when( tagRepository.existsById( testUUID ) ).thenReturn( true );
        when( tagRepository.findById( testUUID ) ).thenReturn( Optional.empty() );

        assertThrows( RuntimeException.class, () -> tagEntityService.saveTag( inputDto ) );
    }
}