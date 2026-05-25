package com.example.productcategory.service;

import com.example.productcategory.dto.ProductRequestDTO;
import com.example.productcategory.dto.ProductResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductResponseDTO> findAll(Long categoryId, Pageable pageable);

    ProductResponseDTO findById(Long id);

    ProductResponseDTO create(ProductRequestDTO productRequestDTO);
}
