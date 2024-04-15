package com.example.adambackend.payload.product;

import java.time.LocalDateTime;

public interface CustomProductFilterRequest {
	Integer getId();

	Double getMinPrice();

	Double getMaxPrice();

	String getProductName();

	String getProductImage();

	String getDescription();

	LocalDateTime getCreateDate();


}
