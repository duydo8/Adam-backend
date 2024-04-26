package com.example.adambackend.payload.discountProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountProductCreate {
	private String discountName;
	private String description;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private Double salePrice;
	private List<Integer> detailOrderListId;
	private Integer eventId;
	private Integer productId;
}
