package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Category;
import com.example.adambackend.repository.CategoryRepository;
import com.example.adambackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category save(Category Category) {
        return categoryRepository.save(Category);
    }

    @Override
    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id);
    }

    @Override
    public List<Category> findAllCategoryParentId() {
        return categoryRepository.findAllCategoryParentId();
    }

    @Override
    public List<Category> findByCategoryParentId(int id) {
        return categoryRepository.findByCategoryParentId(id);
    }
}
