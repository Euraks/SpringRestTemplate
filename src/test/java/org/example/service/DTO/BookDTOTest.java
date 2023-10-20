package org.example.service.DTO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class BookDTOTest {

    @Test
    void testGetUuid() {
        BookDTO bookDTO = new BookDTO();
        UUID uuid = UUID.randomUUID();
        bookDTO.setUuid( uuid );
        assertEquals( uuid, bookDTO.getUuid() );
    }

    @Test
    void testGetBookText() {
        BookDTO bookDTO = new BookDTO();
        String bookText = "Sample book text";
        bookDTO.setBookText( bookText );
        assertEquals( bookText, bookDTO.getBookText() );
    }

    @Test
    void testGetTagEntities() {
        BookDTO bookDTO = new BookDTO();
        List<TagDTO> tagEntities = new ArrayList<>();
        TagDTO tag1 = new TagDTO();
        TagDTO tag2 = new TagDTO();
        tagEntities.add( tag1 );
        tagEntities.add( tag2 );
        bookDTO.setTagEntities( tagEntities );
        assertEquals( tagEntities, bookDTO.getTagEntities() );
    }

    @Test
    void testGetUuidWhenNotSet() {
        BookDTO bookDTO = new BookDTO();
        assertNull( bookDTO.getUuid() );
    }

    @Test
    void testGetBookTextWhenNotSet() {
        BookDTO bookDTO = new BookDTO();
        assertNull( bookDTO.getBookText() );
    }

    @Test
    void testGetTagEntitiesWhenNotSet() {
        BookDTO bookDTO = new BookDTO();
        assertNull( bookDTO.getTagEntities() );
    }
}
