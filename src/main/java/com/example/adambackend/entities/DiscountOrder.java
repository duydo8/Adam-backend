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
@Entity
@Table(name = "discount_orders")
public class DiscountOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String discountName;
	private String description;
	private LocalDateTime createDate;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Integer status;
	private Double salePrice;
	private Double orderMinRange;
	private Double orderMaxRange;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "event_id")
	private Event event;
}
