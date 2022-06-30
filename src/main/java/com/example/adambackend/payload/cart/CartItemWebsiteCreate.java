package com.example.adambackend.payload.cart;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.DetailProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CartItemWebsiteCreate {

    private Integer quantity;

    private Double totalPrice;

    private Integer accountId;

    private Integer detailProductId;


}