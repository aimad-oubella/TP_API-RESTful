package com.example.productcategory.service.impl;

import com.example.productcategory.dto.ProductRequestDTO;
import com.example.productcategory.dto.ProductResponseDTO;
import com.example.productcategory.entity.Category;
import com.example.productcategory.entity.Product;
import com.example.productcategory.exception.ResourceNotFoundException;
import com.example.productcategory.mapper.ProductMapper;
import com.example.productcategory.repository.CategoryRepository;
import com.example.productcategory.repository.ProductRepository;
import com.example.productcategory.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAll(Long categoryId, Pageable pageable) {
        Page<Product> products = categoryId == null
                ? productRepository.findAll(pageable)
                : productRepository.findByCategoryId(categoryId, pageable);

        return products.map(ProductMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        return ProductMapper.toDto(findEntityById(id));
    }

    @Override
    public ProductResponseDTO create(ProductRequestDTO productRequestDTO) {
        Category category = categoryRepository.findById(productRequestDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productRequestDTO.categoryId()));
        Product product = ProductMapper.toEntity(productRequestDTO, category);
        return ProductMapper.toDto(productRepository.save(product));
    }

    private Product findEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
}
