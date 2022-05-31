package com.example.adambackend.service;

import com.example.adambackend.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Optional<Category> findById(Long id);

    void deleteById(Long id);

    Category save(Category category);

    List<Category> findAll();

    List<Category> findAllCategoryParentId();
}
