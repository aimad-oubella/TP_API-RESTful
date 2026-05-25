package com.example.productcategory.repository;

import com.example.productcategory.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByTitle(String title);
}
