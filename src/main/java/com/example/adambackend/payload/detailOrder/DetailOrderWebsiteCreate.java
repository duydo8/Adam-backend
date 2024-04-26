package com.example.adambackend.payload.detailOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrderWebsiteCreate {
	private Integer quantity;
	private Double price;
	private Integer detailProductId;
	private Integer orderId;
}
