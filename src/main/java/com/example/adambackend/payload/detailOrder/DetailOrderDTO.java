package com.example.adambackend.payload.detailOrder;

import java.time.LocalDateTime;

public interface DetailOrderDTO {
	Integer getId();

	Integer getQuantity();

	Double getPrice();

	Double getTotalPrice();

	Boolean getIsDeleted();

	String getDetailOrderCode();

	Boolean getIsActive();

	LocalDateTime getCreateDate();

	String getReason();

	Integer getOrderId();
}
