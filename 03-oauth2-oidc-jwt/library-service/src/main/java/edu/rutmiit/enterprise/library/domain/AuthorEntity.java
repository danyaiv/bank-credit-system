package edu.rutmiit.enterprise.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.util.UUID;

@Entity
@Table(name = "authors")
public class AuthorEntity {
    @Id
    private UUID id;

    @Column(nullable = false, length = 200)
    private String name;

    @Version
    private long version;

    protected AuthorEntity() {
    }

    public AuthorEntity(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

