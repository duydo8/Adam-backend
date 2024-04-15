package com.example.adambackend.entities;

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
@Table(name = "products")
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String productName;
	private String description;
	// 0: not active, 1: active, 2:complete
	private Integer status;
	private String image;
	private Double voteAverage;
	private LocalDateTime createDate;

	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<TagProduct> tagProducts = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	private List<MaterialProduct> materialProducts = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Comment> comments = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<Favorite> favorites = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "product")
	List<DetailProduct> detailProducts = new ArrayList<>();
}
