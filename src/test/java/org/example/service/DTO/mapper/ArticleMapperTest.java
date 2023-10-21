package org.example.service.DTO.mapper;

import org.example.model.entity.Article;
import org.example.service.DTO.ArticleDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArticleMapperTest {

    private ArticleMapper articleMapper;

    @BeforeEach
    public void setUp() {
        articleMapper = Mappers.getMapper( ArticleMapper.class );
    }

    @Test
    void testToDTO() {
        Article article = new Article();
        article.setUuid( UUID.randomUUID() );
        article.setText( "Sample text" );

        ArticleDTO articleDTO = articleMapper.toDTO( article );

        assertEquals( article.getUuid(), articleDTO.getUuid() );
        assertEquals( article.getText(), articleDTO.getText() );
    }

    @Test
    void testToEntity() {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setUuid( UUID.randomUUID() );
        articleDTO.setText( "Sample text" );

        Article article = articleMapper.toEntity( articleDTO );

        assertEquals( articleDTO.getUuid(), article.getUuid() );
        assertEquals( articleDTO.getText(), article.getText() );
    }

    @Test
    void testToDTOList() {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Article article = new Article();
            article.setUuid( UUID.randomUUID() );
            article.setText( "Sample text " + i );
            articles.add( article );
        }

        List<ArticleDTO> articleDTOs = articleMapper.toDTOList( articles );

        assertEquals( articles.size(), articleDTOs.size() );

        for (int i = 0; i < articles.size(); i++) {
            Article article = articles.get( i );
            ArticleDTO articleDTO = articleDTOs.get( i );

            assertEquals( article.getUuid(), articleDTO.getUuid() );
            assertEquals( article.getText(), articleDTO.getText() );
        }
    }

    @Test
    void testToEntityList() {
        List<ArticleDTO> articleDTOs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setUuid( UUID.randomUUID() );
            articleDTO.setText( "Sample text " + i );
            articleDTOs.add( articleDTO );
        }

        List<Article> articles = articleMapper.toEntityList( articleDTOs );

        assertEquals( articleDTOs.size(), articles.size() );

        for (int i = 0; i < articleDTOs.size(); i++) {
            ArticleDTO articleDTO = articleDTOs.get( i );
            Article article = articles.get( i );

            assertEquals( articleDTO.getUuid(), article.getUuid() );
            assertEquals( articleDTO.getText(), article.getText() );
        }
    }
}
