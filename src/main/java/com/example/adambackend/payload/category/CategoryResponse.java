package com.example.adambackend.payload.category;

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
	private Integer status;
	private Integer categoryParentId;
	private List<Category> categoryChildren;
}
