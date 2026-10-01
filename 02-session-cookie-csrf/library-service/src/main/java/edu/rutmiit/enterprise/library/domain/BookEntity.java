package edu.rutmiit.enterprise.library.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

import java.util.UUID;

@Entity
@Table(name = "books")
public class BookEntity {
    @Id
    private UUID id;

    @Column(nullable = false, length = 300)
    private String title;

    @Column(nullable = false, unique = true, length = 20)
    private String isbn;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private AuthorEntity author;

    @Column(name = "publication_year")
    private Integer publicationYear;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private BookStatus status;

    @Version
    private long version;

    protected BookEntity() {
    }

    public BookEntity(UUID id, String title, String isbn, AuthorEntity author, Integer publicationYear) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.publicationYear = publicationYear;
        this.status = BookStatus.AVAILABLE;
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public AuthorEntity getAuthor() {
        return author;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public BookStatus getStatus() {
        return status;
    }

    public long getVersion() {
        return version;
    }
}
