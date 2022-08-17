package com.example.adambackend.payload.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReturn {
    private String orderCode;
    private List<String>  detailCode;
    private Integer status;
    private Double returnPrice;
    private String reason;
    private Double totalPrice;
}
