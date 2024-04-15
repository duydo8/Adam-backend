package com.example.adambackend.payload.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryUpdate {
	private Integer id;
	private String categoryName;
	private Integer status;
	private Integer categoryParentId;
}
