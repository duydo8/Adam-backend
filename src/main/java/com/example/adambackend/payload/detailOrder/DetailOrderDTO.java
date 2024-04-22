package com.example.adambackend.payload.detailOrder;

import java.time.LocalDateTime;

public interface DetailOrderDTO {
	Integer getId();

	Integer getQuantity();

	Double getPrice();

	Double getTotalPrice();

	Integer getStatus();

	String getDetailOrderCode();

	LocalDateTime getCreateDate();

	String getReason();

	Integer getOrderId();
}
