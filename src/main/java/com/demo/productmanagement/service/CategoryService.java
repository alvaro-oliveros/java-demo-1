package com.demo.productmanagement.service;

import com.demo.productmanagement.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAllCategories();

    Optional<Category> getCategoryById(Long id);

    Category saveCategory(Category category);

    boolean updateCategory(Category category);

    boolean deleteCategory(Long id);

}
