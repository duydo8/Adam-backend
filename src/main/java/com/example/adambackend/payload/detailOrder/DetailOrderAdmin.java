package com.example.adambackend.payload.detailOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrderAdmin {
	private Integer id;

	private Integer quantity;

	private Double totalPrice;

	private Integer detailProductId;

	private Integer status;

	private LocalDateTime createDate;

	private String detailOrderCode;
}
