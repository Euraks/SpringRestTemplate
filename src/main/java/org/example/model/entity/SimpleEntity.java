package org.example.model.entity;


import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "simple_entities")
public class SimpleEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "uuid", updatable = false, nullable = false)
    private UUID uuid;

    @Column(name = "description")
    private String description;

    public SimpleEntity() {
    }

    public SimpleEntity(String description) {
        this.description = description;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SimpleEntity that)) return false;
        return Objects.equals( getDescription(), that.getDescription() );
    }

    @Override
    public int hashCode() {
        return Objects.hash( getDescription() );
    }

    @Override
    public String toString() {
        return "SimpleEntity{" +
                "id=" + uuid +
                ", description='" + description + '\'' +
                '}';
    }

}
