package com.example.productcategory.mapper;

import com.example.productcategory.dto.ProductRequestDTO;
import com.example.productcategory.dto.ProductResponseDTO;
import com.example.productcategory.entity.Category;
import com.example.productcategory.entity.Product;

public final class ProductMapper {

    private ProductMapper() {
    }

    public static Product toEntity(ProductRequestDTO dto, Category category) {
        return Product.builder()
                .title(dto.title())
                .description(dto.description())
                .price(dto.price())
                .image(dto.image())
                .category(category)
                .build();
    }

    public static ProductResponseDTO toDto(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getImage(),
                CategoryMapper.toDto(product.getCategory()),
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
