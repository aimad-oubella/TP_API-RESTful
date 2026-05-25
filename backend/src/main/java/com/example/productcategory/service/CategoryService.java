package com.example.productcategory.service;

import com.example.productcategory.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> findAll();

    CategoryDTO findById(Long id);

    CategoryDTO create(CategoryDTO categoryDTO);
}
