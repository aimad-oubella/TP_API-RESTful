package com.example.productcategory.mapper;

import com.example.productcategory.dto.CategoryDTO;
import com.example.productcategory.entity.Category;

public final class CategoryMapper {

    private CategoryMapper() {
    }

    public static CategoryDTO toDto(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getTitle(),
                category.getDescription(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }

    public static Category toEntity(CategoryDTO dto) {
        return Category.builder()
                .title(dto.title())
                .description(dto.description())
                .build();
    }
}
