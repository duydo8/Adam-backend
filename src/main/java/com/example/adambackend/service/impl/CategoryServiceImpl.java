package com.example.adambackend.service.impl;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Category;
import com.example.adambackend.payload.category.CategoryResponse;
import com.example.adambackend.payload.category.CategoryUpdate;
import com.example.adambackend.repository.CategoryRepository;
import com.example.adambackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public List<Category> findAll(String name) {
		if (CommonUtil.isNotNull(name)) {
			return categoryRepository.findAll(name);
		}
		return categoryRepository.findAll();
	}

	@Override
	public void updateCategoriesDeleted(Integer id) {
		categoryRepository.updateCategoriesDeleted(id);
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
	public List<Category> findByCategoryParentId(int id) {
		return categoryRepository.findByCategoryParentId(id);
	}

	@Override
	public List<CategoryResponse> findAllCategory() {
		List<CategoryResponse> categoryResponseList = new ArrayList<>();
		List<Category> categories = categoryRepository.findAllCategoryParent();
		for (Category category : categories) {
			CategoryResponse categoryResponse = new CategoryResponse();
			categoryResponse.setCategoryParentId(category.getCategoryParentId());
			categoryResponse.setId(category.getId());
			categoryResponse.setCategoryName(category.getCategoryName());
			categoryResponse.setStatus(category.getStatus());
			categoryResponse.setCategoryChildren(categoryRepository.findByCategoryParentId(category.getId()));
			categoryResponseList.add(categoryResponse);
		}
		return categoryResponseList;
	}

	@Override
	public Category updateCategory(CategoryUpdate categoryUpdate) {
		Optional<Category> categoryOptional = findById(categoryUpdate.getId());
		if (categoryOptional.isPresent()) {
			Category category = categoryOptional.get();
			category.setCategoryName(categoryUpdate.getCategoryName());
			category.setStatus(categoryUpdate.getStatus());
			category.setCategoryParentId(categoryUpdate.getCategoryParentId());
			return categoryRepository.save(category);
		} else {
			return null;
		}
	}
}
