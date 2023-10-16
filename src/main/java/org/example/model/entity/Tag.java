package org.example.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Tag {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    private String tagName;

    @ManyToMany()
    private List<Book> bookEntities;

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public List<Book> getBookEntities() {
        return bookEntities;
    }

    public void setBookEntities(List<Book> bookEntities) {
        this.bookEntities = bookEntities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return Objects.equals( getUuid(), tag.getUuid() ) && Objects.equals( getTagName(), tag.getTagName() ) && Objects.equals( getBookEntities(), tag.getBookEntities() );
    }

    @Override
    public int hashCode() {
        return Objects.hash( getUuid(), getTagName(), getBookEntities() );
    }
}
