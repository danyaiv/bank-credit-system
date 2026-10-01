package edu.rutmiit.enterprise.library.repository;

import edu.rutmiit.enterprise.library.domain.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AuthorRepository extends JpaRepository<AuthorEntity, UUID> {
}

