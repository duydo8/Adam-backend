package com.example.adambackend.payload.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CartItemWebsiteCreate {

    private Integer quantity;

    private Double totalPrice;

    private Integer accountId;

    private Integer detailProductId;


}
