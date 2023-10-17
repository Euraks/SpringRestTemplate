package org.example.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    private String bookText;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Tag> tagEntities = new ArrayList<>();;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getBookText() {
        return bookText;
    }

    public void setBookText(String bookText) {
        this.bookText = bookText;
    }

    public List<Tag> getTagEntities() {
        return tagEntities;
    }

    public void setTagEntities(List<Tag> tagEntities) {
        this.tagEntities = tagEntities;
    }

    public void addTag(Tag tag) {
        this.tagEntities.add(tag);
        tag.getBookEntities().add(this);
    }

    public void removeTag(Tag tag) {
        this.tagEntities.remove(tag);
        tag.getBookEntities().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;
        Book book = (Book) o;
        return Objects.equals( getUuid(), book.getUuid() ) && Objects.equals( getBookText(), book.getBookText() );
    }

    @Override
    public int hashCode() {
        return Objects.hash( getUuid(), getBookText() );
    }
}
