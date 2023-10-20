package org.example.service.DTO.mapper;

import org.example.model.entity.Tag;
import org.example.service.DTO.TagDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TagMapperTest {

    private TagMapper tagMapper;

    @BeforeEach
    public void setUp() {
        tagMapper = Mappers.getMapper( TagMapper.class );
    }

    @Test
    void testToDTO() {
        Tag entity = new Tag();
        entity.setTagName( "Fiction" );
        entity.setUuid( UUID.randomUUID() );

        TagDTO result = tagMapper.toDTO( entity );

        assertEquals( entity.getTagName(), result.getTagName() );
        assertEquals( entity.getUuid(), result.getUuid() );
        assertNull( result.getBookEntities() );
    }

    @Test
    void testToEntity() {
        TagDTO dto = new TagDTO();
        dto.setTagName( "Non-Fiction" );
        dto.setUuid( UUID.randomUUID() );

        Tag result = tagMapper.toEntity( dto );

        assertEquals( dto.getTagName(), result.getTagName() );
        assertEquals( dto.getUuid(), result.getUuid() );
    }

    @Test
    void testToDTOList() {
        Tag entity1 = new Tag();
        entity1.setTagName( "Fiction" );
        entity1.setUuid( UUID.randomUUID() );

        Tag entity2 = new Tag();
        entity2.setTagName( "Mystery" );
        entity2.setUuid( UUID.randomUUID() );

        List<Tag> entities = Arrays.asList( entity1, entity2 );

        List<TagDTO> results = tagMapper.toDTOList( entities );

        assertEquals( entities.size(), results.size() );
        assertEquals( entity1.getTagName(), results.get( 0 ).getTagName() );
        assertEquals( entity1.getUuid(), results.get( 0 ).getUuid() );
        assertNull( results.get( 0 ).getBookEntities() );
        assertEquals( entity2.getTagName(), results.get( 1 ).getTagName() );
        assertEquals( entity2.getUuid(), results.get( 1 ).getUuid() );
        assertNull( results.get( 1 ).getBookEntities() );
    }

    @Test
    void testToEntityList() {
        TagDTO dto1 = new TagDTO();
        dto1.setTagName( "History" );
        dto1.setUuid( UUID.randomUUID() );

        TagDTO dto2 = new TagDTO();
        dto2.setTagName( "Romance" );
        dto2.setUuid( UUID.randomUUID() );

        List<TagDTO> dtos = Arrays.asList( dto1, dto2 );

        List<Tag> results = tagMapper.toEntityList( dtos );

        assertEquals( dtos.size(), results.size() );
        assertEquals( dto1.getTagName(), results.get( 0 ).getTagName() );
        assertEquals( dto1.getUuid(), results.get( 0 ).getUuid() );
        assertEquals( dto2.getTagName(), results.get( 1 ).getTagName() );
        assertEquals( dto2.getUuid(), results.get( 1 ).getUuid() );
    }
}
