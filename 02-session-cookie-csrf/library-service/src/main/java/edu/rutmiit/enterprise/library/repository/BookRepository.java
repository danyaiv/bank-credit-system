package edu.rutmiit.enterprise.library.repository;

import edu.rutmiit.enterprise.library.domain.BookEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<BookEntity, UUID> {
    boolean existsByIsbn(String isbn);

    @Override
    @EntityGraph(attributePaths = "author")
    List<BookEntity> findAll();

    @Override
    @EntityGraph(attributePaths = "author")
    Optional<BookEntity> findById(UUID id);
}

