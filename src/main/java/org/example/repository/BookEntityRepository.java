package org.example.repository;

import org.example.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookEntityRepository extends JpaRepository<Book, UUID> {
}
