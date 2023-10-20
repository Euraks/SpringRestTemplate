package org.example.service.DTO;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class AuthorEntityDTOTest {

    @Test
    void testGetUuid() {
        AuthorEntityDTO authorDTO = new AuthorEntityDTO();
        UUID uuid = UUID.randomUUID();
        authorDTO.setUuid( uuid );
        assertEquals( uuid, authorDTO.getUuid() );
    }

    @Test
    void testGetAuthorName() {
        AuthorEntityDTO authorDTO = new AuthorEntityDTO();
        String authorName = "John Doe";
        authorDTO.setAuthorName( authorName );
        assertEquals( authorName, authorDTO.getAuthorName() );
    }

    @Test
    void testGetArticleList() {
        AuthorEntityDTO authorDTO = new AuthorEntityDTO();
        List<ArticleDTO> articleList = new ArrayList<>();
        ArticleDTO article1 = new ArticleDTO();
        ArticleDTO article2 = new ArticleDTO();
        articleList.add( article1 );
        articleList.add( article2 );
        authorDTO.setArticleList( articleList );
        assertEquals( articleList, authorDTO.getArticleList() );
    }

    @Test
    void testGetUuidWhenNotSet() {
        AuthorEntityDTO authorDTO = new AuthorEntityDTO();
        assertNull( authorDTO.getUuid() );
    }

    @Test
    void testGetAuthorNameWhenNotSet() {
        AuthorEntityDTO authorDTO = new AuthorEntityDTO();
        assertNull( authorDTO.getAuthorName() );
    }
}
