package com.example.adambackend.payload.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdatePayBack {
    @ElementCollection
    List<Integer> cartItemIds;
    private Integer orderId;
}
