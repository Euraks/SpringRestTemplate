package org.example.repository;

import org.example.model.entity.SimpleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SimpleEntityRepository extends JpaRepository<SimpleEntity, UUID> {
}
