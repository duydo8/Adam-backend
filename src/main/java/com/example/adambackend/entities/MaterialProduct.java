package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "material_products")
public class MaterialProduct {
	@EmbeddedId
	private MaterialProductPK materialProductPK;
	private Integer status;
	private LocalDateTime createDate;

	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name = "product_id")
	private Product product = new Product();

	@ManyToOne
	@MapsId("materialId")
	@JoinColumn(name = "material_id")
	private Material material = new Material();
}
