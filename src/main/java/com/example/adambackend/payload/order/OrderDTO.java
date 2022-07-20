package com.example.adambackend.payload.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Integer id;
    private List<Integer> detailOrderIds = new ArrayList<>();
}
