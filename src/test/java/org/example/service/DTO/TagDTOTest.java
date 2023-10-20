package org.example.service.DTO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TagDTOTest {

    @Test
    void testGetUuid() {
        TagDTO tagDTO = new TagDTO();
        UUID uuid = UUID.randomUUID();
        tagDTO.setUuid( uuid );
        assertEquals( uuid, tagDTO.getUuid() );
    }

    @Test
    void testGetTagName() {
        TagDTO tagDTO = new TagDTO();
        String tagName = "Sample Tag";
        tagDTO.setTagName( tagName );
        assertEquals( tagName, tagDTO.getTagName() );
    }

    @Test
    void testGetBookEntities() {
        TagDTO tagDTO = new TagDTO();
        List<BookDTO> bookEntities = new ArrayList<>();
        tagDTO.setBookEntities( bookEntities );
        assertEquals( bookEntities, tagDTO.getBookEntities() );
    }

    @Test
    void testGetUuidWhenNotSet() {
        TagDTO tagDTO = new TagDTO();
        assertNull( tagDTO.getUuid() );
    }

    @Test
    void testGetTagNameWhenNotSet() {
        TagDTO tagDTO = new TagDTO();
        assertNull( tagDTO.getTagName() );
    }

    @Test
    void testGetBookEntitiesWhenNotSet() {
        TagDTO tagDTO = new TagDTO();
        assertNull( tagDTO.getBookEntities() );
    }
}
