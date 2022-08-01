package com.example.adambackend.payload.order;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Address;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
