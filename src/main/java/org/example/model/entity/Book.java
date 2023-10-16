package org.example.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "uuid")
public class Book {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    private String bookText;

    @ManyToMany(mappedBy = "bookEntities", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
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
