package edu.rutmiit.enterprise.library.api;

import edu.rutmiit.enterprise.library.domain.BookStatus;

import java.util.UUID;

public record BookResponse(
        UUID id,
        String title,
        String isbn,
        UUID authorId,
        String authorName,
        Integer publicationYear,
        BookStatus status
) {
}
