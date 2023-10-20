package org.example.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class AuthorEntityTest {

    private AuthorEntity author;
    private final UUID uuid = UUID.randomUUID();
    private final String authorName = "John Doe";
    private Article article1;
    private Article article2;

    @BeforeEach
    public void setUp() {
        author = new AuthorEntity();
        author.setUuid( uuid );
        author.setAuthorName( authorName );

        article1 = new Article( "Text1" );
        article2 = new Article( "Text2" );
    }

    @Test
    void gettersAndSettersWorkCorrectly() {
        assertThat( author.getUuid() ).isEqualTo( uuid );
        assertThat( author.getAuthorName() ).isEqualTo( authorName );
    }

    @Test
    void equalsAndHashCodeWorkCorrectly() {
        AuthorEntity anotherAuthor = new AuthorEntity();
        anotherAuthor.setUuid( uuid );
        anotherAuthor.setAuthorName( authorName );

        assertThat( author ).isEqualTo( anotherAuthor );
        assertThat( author.hashCode() ).isEqualTo( anotherAuthor.hashCode() );

        anotherAuthor.setAuthorName( "Different Name" );
        assertThat( author ).isNotEqualTo( anotherAuthor );
    }
}
