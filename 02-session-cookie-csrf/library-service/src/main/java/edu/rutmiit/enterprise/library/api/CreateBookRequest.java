package edu.rutmiit.enterprise.library.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreateBookRequest(
        @NotBlank @Size(max = 300) String title,
        @NotBlank @Pattern(regexp = "^(97[89])?\\d{9}[\\dX]$") String isbn,
        @NotNull UUID authorId,
        @Min(1450) @Max(3000) Integer publicationYear
) {
}
