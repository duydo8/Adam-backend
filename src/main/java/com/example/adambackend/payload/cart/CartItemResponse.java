package com.example.adambackend.payload.cart;

import java.time.LocalDateTime;

public interface CartItemResponse {
    Integer getId();

    Integer getQuantity();

    Double getTotalPrice();

    Integer getAccountId();

    Integer getDetailProductId();

    Boolean getIsActive();

    LocalDateTime getCreateDate();
}
