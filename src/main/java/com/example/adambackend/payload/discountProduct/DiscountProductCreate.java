package com.example.adambackend.payload.discountProduct;

import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.entities.Event;
import com.example.adambackend.entities.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiscountProductCreate {
    private String discountName;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double salePrice;
    private List<Integer> detailOrderListId;
    private Integer eventId;
    private Integer productId;
}
