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
@Table(name = "tag_products")
public class TagProduct {
	@EmbeddedId
	private TagProductPK tagProductPK;
	private Integer status;
	@Column(name = "create_date")
	private LocalDateTime createDate;

	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name = "product_id")
	private Product product = new Product();

	@ManyToOne
	@MapsId("tagId")
	@JoinColumn(name = "tag_id")
	private Tag tag = new Tag();
}
