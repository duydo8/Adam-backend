package com.example.adambackend.payload.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderReturn {
    private Integer orderCode;
    private Integer detailId;
    private Integer status;
    private Double price;
    private String reason;
}
