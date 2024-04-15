package com.example.adambackend.payload.product;

import com.example.adambackend.entities.Category;
import com.example.adambackend.entities.Material;
import com.example.adambackend.entities.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {
	private Integer id;
	private String productName;
	private String description;
	private String image;
	private Double voteAverage;
	private LocalDateTime createDate;
	private Integer status;
	private Category category;
	private List<Tag> tagList;
	private List<Material> materialList;
}
