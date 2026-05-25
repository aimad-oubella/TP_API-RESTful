package com.example.productcategory.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CategoryDTO(
        Long id,
        @NotBlank(message = "The title field is required.")
        String title,
        String description,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
