package com.example.productcategory.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponseDTO(
        Long id,
        String title,
        String description,
        BigDecimal price,
        String image,
        CategoryDTO category,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
