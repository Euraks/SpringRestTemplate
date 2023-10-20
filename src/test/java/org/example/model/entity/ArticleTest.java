package org.example.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

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
    public void testEqualsSameObject() {
        Article article = new Article(UUID.randomUUID(), "Test article");
        assertTrue(article.equals(article));
    }

    @Test
    public void testEqualsNullObject() {
        Article article = new Article(UUID.randomUUID(), "Test article");
        assertFalse(article.equals(null));
    }

    @Test
    public void testEqualsDifferentClass() {
        Article article = new Article(UUID.randomUUID(), "Test article");
        AuthorEntity author = mock(AuthorEntity.class);
        assertFalse(article.equals(author));
    }

    // Добавленные тесты для проверки условий
    @Test
    public void testEqualsWithSameClass() {
        Article article1 = new Article(UUID.randomUUID(), "Test article");
        Article article2 = new Article(UUID.randomUUID(), "Another article");
        assertFalse(article1.equals(article2));
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
