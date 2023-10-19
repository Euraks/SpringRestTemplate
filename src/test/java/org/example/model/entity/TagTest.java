package org.example.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class TagTest {

    private Tag tag;
    private final UUID uuid = UUID.randomUUID();
    private final String tagName = "Sample Tag";
    private Book book1;
    private Book book2;

    @BeforeEach
    public void setUp() {
        tag = new Tag();
        tag.setUuid(uuid);
        tag.setTagName(tagName);

        book1 = new Book();
        book2 = new Book();
    }

    @Test
    void gettersAndSettersWorkCorrectly() {
        assertThat(tag.getUuid()).isEqualTo(uuid);
        assertThat(tag.getTagName()).isEqualTo(tagName);
    }

    @Test
    void equalsAndHashCodeWorkCorrectly() {
        Tag anotherTag = new Tag();
        anotherTag.setUuid(uuid);
        anotherTag.setTagName(tagName);

        assertThat(tag).isEqualTo(anotherTag);
        assertThat(tag.hashCode()).isEqualTo(anotherTag.hashCode());

        anotherTag.setTagName("Different Tag");
        assertThat(tag).isNotEqualTo(anotherTag);
    }

    @Test
    void manyToManyRelationWorksCorrectly() {
        tag.addBook(book1);
        tag.addBook(book2);

        assertThat(book1.getTagEntities()).containsExactly(tag);
        assertThat(book2.getTagEntities()).containsExactly(tag);
        assertThat(tag.getBookEntities()).containsExactly(book1, book2);

        tag.removeBook(book1);

        assertThat(book1.getTagEntities()).isEmpty();
        assertThat(tag.getBookEntities()).containsExactly(book2);
    }
}
