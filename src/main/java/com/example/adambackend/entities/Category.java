package com.example.adambackend.entities;

import com.example.adambackend.payload.category.CategoryDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "categories")
@Entity
public class Category {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String categoryName;
	private Integer status;
	private LocalDateTime createDate;
	private Integer categoryParentId;

	@JsonIgnore
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	List<Product> products = new ArrayList<>();

	public Category(CategoryDTO categoryDTO){
		this.categoryName = categoryDTO.getCategoryName();
		this.status = 1;
		this.createDate = LocalDateTime.now();
		this.categoryParentId = categoryDTO.getCategoryParentId();
	}
}
