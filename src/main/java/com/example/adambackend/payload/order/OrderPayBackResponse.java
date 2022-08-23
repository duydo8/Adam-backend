package com.example.adambackend.payload.order;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderPayBackResponse {
    private Integer quantity;
    private Double totalPrice;

}
