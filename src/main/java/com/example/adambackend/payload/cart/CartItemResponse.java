package com.example.adambackend.payload.cart;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.DetailProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
