package com.example.adambackend.payload;

import com.example.adambackend.entities.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private Integer id;

    private String categoryName;

    private Boolean isDeleted;

    private Integer categoryParentId;
    private List<Category> categoryChildren;
}
