package edu.rutmiit.enterprise.library.service;

import edu.rutmiit.enterprise.library.api.ApiException;
import edu.rutmiit.enterprise.library.api.AuthorResponse;
import edu.rutmiit.enterprise.library.api.BookResponse;
import edu.rutmiit.enterprise.library.api.CreateAuthorRequest;
import edu.rutmiit.enterprise.library.api.CreateBookRequest;
import edu.rutmiit.enterprise.library.domain.AuthorEntity;
import edu.rutmiit.enterprise.library.domain.BookEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class BookService {
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public BookService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Transactional
    public AuthorResponse createAuthor(CreateAuthorRequest request) {
        AuthorEntity author = authorRepository.save(
                new AuthorEntity(UUID.randomUUID(), request.name().trim())
        );
        return new AuthorResponse(author.getId(), author.getName());
    }

    @Transactional(readOnly = true)
    public List<AuthorResponse> authors() {
        return authorRepository.findAll().stream()
                .map(author -> new AuthorResponse(author.getId(), author.getName()))
                .toList();
    }

    @Transactional
    public BookResponse createBook(CreateBookRequest request) {
        if (bookRepository.existsByIsbn(request.isbn())) {
            throw new ApiException(HttpStatus.CONFLICT, "Книга с таким ISBN уже существует");
        }
        AuthorEntity author = authorRepository.findById(request.authorId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Автор не найден"));
        BookEntity book = bookRepository.save(new BookEntity(
                UUID.randomUUID(),
                request.title().trim(),
                request.isbn(),
                author,
                request.publicationYear()
        ));
        return toResponse(book);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> books() {
        return bookRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public BookResponse book(UUID id) {
        BookEntity book = bookRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Книга не найдена"));
        return toResponse(book);
    }

    private BookResponse toResponse(BookEntity book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getAuthor().getId(),
                book.getAuthor().getName(),
                book.getPublicationYear(),
                book.getStatus()
        );
    }
}
