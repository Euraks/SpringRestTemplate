package org.example.repository;

import org.example.model.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorEntityRepository extends JpaRepository<AuthorEntity, UUID> {

    @Query("SELECT a FROM AuthorEntity a JOIN FETCH a.articleList WHERE a.uuid = :uuid")
    Optional<AuthorEntity> findByIdWithArticles(@Param("uuid") UUID uuid);

    @Query("SELECT a FROM AuthorEntity a JOIN FETCH a.articleList")
    List<AuthorEntity> findAllWithArticles();
}
