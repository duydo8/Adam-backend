package com.example.adambackend.payload.cart;

import com.example.adambackend.entities.DetailProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDTO {
    Integer id;
    Integer quantity;
    Double totalPrice;
    Integer accountId;
    DetailProduct detailProduct;
    Boolean isActive;
    LocalDateTime createDate;
}
