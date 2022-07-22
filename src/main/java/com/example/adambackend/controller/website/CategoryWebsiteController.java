package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Category;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.category.CategoryResponse;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("category")
public class CategoryWebsiteController {
    @Autowired
    CategoryRepository categoryService;

    @GetMapping("findAllCategoryParentId")
    public ResponseEntity<IGenericResponse> findAllCategoryParentId() {
        try {
            List<CategoryResponse> categoryResponseList = new ArrayList<>();
            List<Category> categories = categoryService.findAllCategoryParentId();
            for (Category category : categories
            ) {
                CategoryResponse categoryResponse = new CategoryResponse();
                categoryResponse.setCategoryParentId(category.getCategoryParentId());
                categoryResponse.setId(category.getId());
                categoryResponse.setCategoryName(category.getCategoryName());
                categoryResponse.setIsDeleted(category.getIsDeleted());
                categoryResponse.setCategoryChildren(categoryService.findByCategoryParentId(category.getId()));
                categoryResponseList.add(categoryResponse);
                categoryResponse.setIsActive(category.getIsActive());
            }
            return ResponseEntity.ok().body(new IGenericResponse<List<CategoryResponse>>(categoryResponseList, 200, "findAll Category parent successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("findAll")
    public ResponseEntity<?> findAll(@RequestParam(value = "name",required = false)String name) {
        try {
            if(name==null){
                return ResponseEntity.ok().body(new IGenericResponse<>(categoryService.findAll(), 200, ""));

            } return ResponseEntity.ok().body(new IGenericResponse<>(categoryService.findAll(name), 200, ""));
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

                return ResponseEntity.ok().body(new IGenericResponse<>(categoryService.findByCategoryParentId(id), 200, ""));

            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy category"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
}
