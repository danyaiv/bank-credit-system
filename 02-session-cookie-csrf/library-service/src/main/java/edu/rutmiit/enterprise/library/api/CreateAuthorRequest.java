package edu.rutmiit.enterprise.library.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateAuthorRequest(
        @NotBlank @Size(max = 200) String name
) {
}

