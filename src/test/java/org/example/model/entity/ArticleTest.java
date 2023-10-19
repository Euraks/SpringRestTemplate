package org.example.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ArticleTest {

    private Article article;
    private final UUID uuid = UUID.randomUUID();
    private final AuthorEntity author = new AuthorEntity();
    private final String text = "Sample Text";

    @BeforeEach
    public void setUp() {
        article = new Article();
        article.setUuid( uuid );
        article.setAuthor( author );
        article.setText( text );
    }

    @Test
    void gettersAndSettersWorkCorrectly() {
        assertThat( article.getUuid() ).isEqualTo( uuid );
        assertThat( article.getAuthor() ).isEqualTo( author );
        assertThat( article.getText() ).isEqualTo( text );
    }

    @Test
    void equalsAndHashCodeWorkCorrectly() {
        Article anotherArticle = new Article( uuid, text );
        anotherArticle.setAuthor( author );

        assertThat( article ).isEqualTo( anotherArticle );
        assertThat( article.hashCode() ).isEqualTo( anotherArticle.hashCode() );

        anotherArticle.setText( "Different text" );
        assertThat( article ).isNotEqualTo( anotherArticle );
    }

    @Test
    void toStringReturnsCorrectString() {
        String expectedString = "Article{uuid=" + uuid + ", author=" + author + ", text='" + text + "'}";
        assertThat( article.toString() ).isEqualTo( expectedString );
    }
}
