package com.example.adambackend.payload.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDashboard {
    private String name;
    private List<String> labels;
    private List<Double> data;
}
