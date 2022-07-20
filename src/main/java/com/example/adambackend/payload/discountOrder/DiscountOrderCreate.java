package com.example.adambackend.payload.discountOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountOrderCreate {
    private String discountName;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double salePrice;
    private Double orderMinRange;
    private Double orderMaxRange;
    private Integer eventId;
}
