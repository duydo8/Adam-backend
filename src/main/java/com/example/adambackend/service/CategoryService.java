package com.example.adambackend.service;

import java.util.List;
import java.util.Optional;

import com.example.adambackend.entities.Category;

public interface CategoryService {

	Optional<Category> findById(Long id);

	void deleteById(Long id);

	Category create(Category category);

	List<Category> findAll();
}
