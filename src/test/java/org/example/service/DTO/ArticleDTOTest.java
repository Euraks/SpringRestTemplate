package org.example.service.DTO;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ArticleDTOTest {

    @Test
    void testGetUuid() {
        ArticleDTO articleDTO = new ArticleDTO();

        UUID uuid = UUID.randomUUID();
        articleDTO.setUuid( uuid );

        assertEquals( uuid, articleDTO.getUuid() );
    }

    @Test
    void testGetText() {
        ArticleDTO articleDTO = new ArticleDTO();

        String text = "Sample text";
        articleDTO.setText( text );

        assertEquals( text, articleDTO.getText() );
    }

    @Test
    void testGetUuidWhenNotSet() {
        ArticleDTO articleDTO = new ArticleDTO();

        assertNull( articleDTO.getUuid() );
    }

    @Test
    void testGetTextWhenNotSet() {
        ArticleDTO articleDTO = new ArticleDTO();


        assertNull( articleDTO.getText() );
    }
}
