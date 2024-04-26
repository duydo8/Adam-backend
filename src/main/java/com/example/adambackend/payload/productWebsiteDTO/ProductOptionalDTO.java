package com.example.adambackend.payload.productWebsiteDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductOptionalDTO {
	private Integer id;
	private String description;
	private Integer status;
	private Double maxPrice;
	private Double minPrice;
	private String productName;
	private Double voteAverage;
	private Boolean isFavorite;
	private List<OptionProduct> options;
}
