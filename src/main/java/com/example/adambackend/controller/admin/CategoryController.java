package com.example.adambackend.controller.admin;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Category;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.category.CategoryDTO;
import com.example.adambackend.payload.category.CategoryUpdate;
import com.example.adambackend.payload.category.ListCategoryId;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@PostMapping("create")
	public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
		try {
			if (CommonUtil.isNotNull(categoryDTO.getCategoryParentId()) && categoryDTO.getCategoryParentId() <= 0) {
				categoryDTO.setCategoryName(null);
			}
			return ResponseEntity.ok().body(new IGenericResponse(new Category(categoryDTO), 200, ""));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findAllCategoryParent")
	public ResponseEntity<IGenericResponse> findAllCategoryParent() {
		try {
			return ResponseEntity.ok().body(new IGenericResponse(categoryService.findAllCategory(), 200,
					"findAll Category parent successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> updateCategory(@RequestBody CategoryUpdate categoryUpdate) {
		try {
			Category category = categoryService.updateCategory(categoryUpdate);
			if (CommonUtil.isNotNull(category)) {
				return ResponseEntity.ok().body(new IGenericResponse(category, 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> deleteEvent(@RequestParam("category_id") Integer id) {
		try {
			Optional<Category> categoryOptional = categoryService.findById(id);
			if (categoryOptional.isPresent()) {
				categoryService.deleteById(id);
				return ResponseEntity.ok().body(new HandleExceptionDemo(200, "successfully"));
			} else {
				return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found category"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findAll")
	public ResponseEntity<?> findAll(@RequestParam(value = "name", required = false) String name) {
		try {
			return ResponseEntity.ok().body(new IGenericResponse<>(categoryService.findAll(name), 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("deleteByListId")
	public ResponseEntity<?> deleteArrayTagId(@RequestBody ListCategoryId listCategoryId) {
		try {
			List<Integer> listId = listCategoryId.getListCategoryId();
			if (!listId.isEmpty()) {
				for (Integer id : listId) {
					Optional<Category> categoryOptional = categoryService.findById(id);
					if (categoryOptional.isPresent()) {
						categoryService.updateCategoriesDeleted(id);
					}
				}
				return ResponseEntity.ok().body(new IGenericResponse<>("", 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không thể thực hiện "));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findCategoryByParentId")
	public ResponseEntity<?> findCategoryByParentId(@RequestParam("category_parent_id") Integer id) {
		try {
			Optional<Category> categoryOptional = categoryService.findById(id);
			if (categoryOptional.isPresent()) {
				return ResponseEntity.ok().body(new IGenericResponse<>(categoryService.findByCategoryParentId(id), 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy category"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
