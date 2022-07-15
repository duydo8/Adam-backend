package com.example.adambackend.payload.discountOrder;

import com.example.adambackend.entities.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
