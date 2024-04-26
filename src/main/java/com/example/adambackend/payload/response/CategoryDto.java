package com.example.adambackend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	private Integer id;
	private String categoryName;
	private Integer status;
	private Integer categoryParentId;
}
