package com.example.adambackend.payload.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CartItemWebsiteUpdate {
    private Integer id;
    private Integer quantity;
    private Double totalPrice;

}
