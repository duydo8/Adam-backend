package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Category;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryWebsiteController {
    @Autowired
    CategoryService categoryService;
    @GetMapping("findAllCategoryParentId")
    public ResponseEntity<IGenericResponse> findAllCategoryParentId(){
        return ResponseEntity.ok().body(new IGenericResponse<List<Category>>(categoryService.findAllCategoryParentId(),200,"findAll Category parent successfully"));

    }
}
