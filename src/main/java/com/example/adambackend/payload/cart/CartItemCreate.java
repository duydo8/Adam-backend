package com.example.adambackend.payload.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CartItemCreate {
	private Integer quantity;
	private Integer accountId;
	private Integer detailProductId;
}
