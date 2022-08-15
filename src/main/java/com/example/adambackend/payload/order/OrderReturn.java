package com.example.adambackend.payload.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReturn {
    private String orderCode;
    private String detailCode;
    private Integer status;
    private Double returnPrice;
    private String reason;
    private Double totalPrice;
}
