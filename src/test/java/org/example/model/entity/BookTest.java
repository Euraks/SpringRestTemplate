package org.example.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class BookTest {

    private Book book;
    private final UUID uuid = UUID.randomUUID();
    private final String bookText = "Sample Book Text";
    private Tag tag1;
    private Tag tag2;

    @BeforeEach
    public void setUp() {
        book = new Book();
        book.setUuid(uuid);
        book.setBookText(bookText);

        tag1 = new Tag("Tag1");
        tag2 = new Tag("Tag2");
    }

    @Test
    void gettersAndSettersWorkCorrectly() {
        assertThat(book.getUuid()).isEqualTo(uuid);
        assertThat(book.getBookText()).isEqualTo(bookText);
    }

    @Test
    void equalsAndHashCodeWorkCorrectly() {
        Book anotherBook = new Book();
        anotherBook.setUuid(uuid);
        anotherBook.setBookText(bookText);

        assertThat(book).isEqualTo(anotherBook);
        assertThat(book.hashCode()).isEqualTo(anotherBook.hashCode());

        // Additional tests to ensure they aren't equal if a field changes
        anotherBook.setBookText("Different Text");
        assertThat(book).isNotEqualTo(anotherBook);
    }

    @Test
    void manyToManyRelationWorksCorrectly() {
        book.addTag(tag1);
        book.addTag(tag2);

        assertThat(tag1.getBookEntities()).containsExactly(book);
        assertThat(tag2.getBookEntities()).containsExactly(book);
        assertThat(book.getTagEntities()).containsExactly(tag1, tag2);

        book.removeTag(tag1);

        assertThat(tag1.getBookEntities()).isEmpty();
        assertThat(book.getTagEntities()).containsExactly(tag2);
    }
}
