package org.example.repository;

import org.example.model.entity.Book;
import org.example.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TagEntityRepository extends JpaRepository<Tag, UUID> {
    List<Tag> findByBookEntities(Book book);
}

