package com.example.adambackend.payload.detailOrder;

import java.time.LocalDateTime;

public interface DetailOrderAdmin {
	Integer getId();

	Integer getQuantity();

	Double getTotalPrice();

	Integer getDetailProductId();

	Integer status();

	LocalDateTime getCreateDate();
}
