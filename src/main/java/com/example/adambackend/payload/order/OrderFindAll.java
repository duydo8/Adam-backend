package com.example.adambackend.payload.order;

import com.example.adambackend.entities.Account;

import java.time.LocalDateTime;

public interface OrderFindAll {
     Integer getId();
     Integer getStatus();
     LocalDateTime getCreateDate();
     Account getAccount();
     String getFullName();
     String getPhoneNumber();
     Double getAmountPrice();
     Double getSalePrice();
     Double getTotalPrice();
     Integer getAddressId();
     String getAddressDetail();
     String getOrderCode();
}
