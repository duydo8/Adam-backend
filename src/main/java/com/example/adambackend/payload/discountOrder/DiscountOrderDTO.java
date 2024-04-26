package com.example.adambackend.payload.discountOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountOrderDTO {
	private Integer id;
	private String discountName;
	private Integer status;
	private Double salePrice;
	private Double orderMinRange;
	private Double orderMaxRange;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Integer eventId;
}
