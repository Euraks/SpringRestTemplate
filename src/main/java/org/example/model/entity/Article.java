package org.example.model.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;
import java.util.UUID;


@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_uuid")
    private AuthorEntity author;

    @Column(name = "text")
    private String text;

    public Article() {}

    public Article(String text) {
        this.text = text;
    }

    public Article(UUID uuid, String text) {
        this.uuid = uuid;
        this.text = text;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public void setAuthor(AuthorEntity author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return Objects.equals( getUuid(), article.getUuid() ) && Objects.equals( getAuthor(), article.getAuthor() ) && Objects.equals( getText(), article.getText() );
    }

    @Override
    public int hashCode() {
        return Objects.hash( getUuid(), getAuthor(), getText() );
    }

    @Override
    public String toString() {
        return "Article{" +
                "uuid=" + uuid +
                ", author=" + author +
                ", text='" + text + '\'' +
                '}';
    }
}

