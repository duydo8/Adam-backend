package com.example.adambackend.service;

import com.example.adambackend.entities.Category;
import com.example.adambackend.payload.category.CategoryResponse;
import com.example.adambackend.payload.category.CategoryUpdate;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

	Optional<Category> findById(Integer id);

	void deleteById(Integer id);

	Category save(Category category);

	List<Category> findAll(String name);

	void updateCategoriesDeleted(Integer id);

	List<Category> findByCategoryParentId(int id);

	List<CategoryResponse> findAllCategory();

	Category updateCategory(CategoryUpdate categoryUpdate);
}
