package com.example.adambackend.payload.discountOrder;

import com.example.adambackend.entities.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountOrderUpdate {
    private Integer id;
    private String discountName;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Boolean isActive;
    private Boolean isDeleted;
    private Double salePrice;
    private Double orderMinRange;
    private Double orderMaxRange;
    private Integer eventId;
}
