package com.example.adambackend.payload.product;

public interface CustomProductFilterRequest {
    Integer getId();

    Double getPriceBottom();

    Double getPriceTop();

    String getProductName();

    String getProductImage();

    String getCreateDate();


}
