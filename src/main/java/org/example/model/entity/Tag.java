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
public class Tag {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    private String tagName;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Book> bookEntities  = new ArrayList<>();;

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

    public void addBook(Book book) {
        this.bookEntities.add( book );
        book.getTagEntities().add( this );
    }

    public void removeBook(Book book) {
        this.bookEntities.remove( book );
        book.getTagEntities().remove( this );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tag)) return false;
        Tag tag = (Tag) o;
        return Objects.equals( getUuid(), tag.getUuid() ) && Objects.equals( getTagName(), tag.getTagName() );
    }

    @Override
    public int hashCode() {
        return Objects.hash( getUuid(), getTagName() );
    }
}
