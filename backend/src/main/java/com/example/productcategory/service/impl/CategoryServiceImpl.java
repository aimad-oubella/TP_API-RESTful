package com.example.productcategory.service.impl;

import com.example.productcategory.dto.CategoryDTO;
import com.example.productcategory.entity.Category;
import com.example.productcategory.exception.ResourceNotFoundException;
import com.example.productcategory.mapper.CategoryMapper;
import com.example.productcategory.repository.CategoryRepository;
import com.example.productcategory.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        return CategoryMapper.toDto(findEntityById(id));
    }

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category category = CategoryMapper.toEntity(categoryDTO);
        return CategoryMapper.toDto(categoryRepository.save(category));
    }

    public Category findEntityById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }
}
