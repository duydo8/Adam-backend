package com.example.adambackend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
	private Integer id;
	private String productName;
	private String description;
	private Integer status;
	private String image;
}
