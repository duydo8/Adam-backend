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
    CategoryRepository CategoryRepository;
    @Override
    public List<Category> findAll() {
        return CategoryRepository.findAll();
    }

    @Override
    public Category create(Category Category) {
        return CategoryRepository.save(Category);
    }

    @Override
    public void deleteById(Long id) {
        CategoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return CategoryRepository.findById(id);
    }
}
