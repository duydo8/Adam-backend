package com.example.adambackend.payload.product;

import java.time.LocalDateTime;

public interface CustomProductFilterRequest {
    Integer getId();

    Double getPriceBottom();

    Double getPriceTop();

    String getProductName();

    String getProductImage();

    LocalDateTime getCreateDate();


}
