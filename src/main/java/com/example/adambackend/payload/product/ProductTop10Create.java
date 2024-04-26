package com.example.adambackend.payload.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductTop10Create {
	private Integer id;
	private String productName;
	private String description;
	private Integer status;
	private String image;
	private Double voteAverage;
	private LocalDateTime createDate;
	private Double minPrice;
	private Double maxPrice;
}
