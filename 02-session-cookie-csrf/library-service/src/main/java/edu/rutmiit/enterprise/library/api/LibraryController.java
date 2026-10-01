package edu.rutmiit.enterprise.library.api;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class LibraryController {
    private final BookService bookService;

    public LibraryController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/authors")
    @PreAuthorize("hasAnyRole('READER','EDITOR')")
    List<AuthorResponse> authors() {
        return bookService.authors();
    }

    @PostMapping("/authors")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('EDITOR')")
    AuthorResponse createAuthor(@Valid @RequestBody CreateAuthorRequest request) {
        return bookService.createAuthor(request);
    }

    @GetMapping("/books")
    @PreAuthorize("hasAnyRole('READER','EDITOR')")
    List<BookResponse> books() {
        return bookService.books();
    }

    @GetMapping("/books/{id}")
    @PreAuthorize("hasAnyRole('READER','EDITOR')")
    BookResponse book(@PathVariable UUID id) {
        return bookService.book(id);
    }

    @PostMapping("/books")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('EDITOR')")
    BookResponse createBook(@Valid @RequestBody CreateBookRequest request) {
        return bookService.createBook(request);
    }
}

