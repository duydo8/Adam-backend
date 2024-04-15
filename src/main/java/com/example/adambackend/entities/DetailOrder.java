package com.example.adambackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detail_orders")
@Entity
public class DetailOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer quantity;
	private Double price;
	private Double totalPrice;
	private String detailOrderCode;
	private Integer status;
	private LocalDateTime createDate;
	private String reason;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "detail_product_id")
	private DetailProduct detailProduct;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "order_id")
	private Order order;
}
