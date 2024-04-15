package com.example.adambackend.payload.order;

import java.time.LocalDateTime;

public interface OrderFindAll {
	Integer getId();

	Integer getStatus();

	LocalDateTime getCreateDate();

	Integer getAccountId();

	String getFullName();

	String getPhoneNumber();

	Double getAmountPrice();

	Double getSalePrice();

	Double getTotalPrice();

	Integer getAddressId();

	String getAddressDetail();

	String getOrderCode();
}
