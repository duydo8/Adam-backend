package com.example.adambackend.payload.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
	private String productName;
	private String description;
	private String image;
	private Integer categoryId;
}
