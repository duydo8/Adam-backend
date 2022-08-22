package com.example.adambackend.payload.discountOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountOrderUpdate {
    private Integer id;
    private String discountName;
    private Boolean isActive;
    private Double salePrice;
    private Double orderMinRange;
    private Double orderMaxRange;

}
