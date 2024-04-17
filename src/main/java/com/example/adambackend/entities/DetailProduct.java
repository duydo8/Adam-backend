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
@Table(name = "detail_products")
@Entity
public class DetailProduct {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer quantity;
	private Double priceImport;
	private Double priceExport;
	private String imageProduct;
	private Integer status;
	private LocalDateTime createDate;

	@ManyToOne
	@JoinColumn(name = "color_id")
	private Color color;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "size_id")
	private Size size;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;

	@JsonIgnore
	@OneToMany(mappedBy = "detailProduct")
	List<CartItems> cartItems = new ArrayList<>();
}
