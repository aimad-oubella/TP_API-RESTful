package com.example.productcategory.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequestDTO(
        @NotBlank(message = "The title field is required.")
        String title,

        @NotBlank(message = "The description field is required.")
        String description,

        @NotNull(message = "The price field is required.")
        @Positive(message = "The price must be greater than 0.")
        @Digits(integer = 8, fraction = 2, message = "The price must have at most 10 digits with 2 decimals.")
        BigDecimal price,

        String image,

        @NotNull(message = "The category field is required.")
        Long categoryId
) {
}
