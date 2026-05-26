package com.example.productcategory.controller;

import com.example.productcategory.dto.ProductRequestDTO;
import com.example.productcategory.dto.ProductResponseDTO;
import com.example.productcategory.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public Page<ProductResponseDTO> findAll(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(name = "category_id", required = false) Long categoryIdSnakeCase,
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Long resolvedCategoryId = categoryId != null ? categoryId : categoryIdSnakeCase;
        return productService.findAll(resolvedCategoryId, pageable);
    }

    @GetMapping("/{id}")
    public ProductResponseDTO findById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponseDTO create(@Valid @RequestBody ProductRequestDTO productRequestDTO) {
        return productService.create(productRequestDTO);
    }
}
