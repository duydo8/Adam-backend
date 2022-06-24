package com.example.adambackend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String productName;
    private String description;
    private String image;
    private Integer categoryId;
    private List<Integer> tagProductIdList;
    private List<Integer> materialProductIdList;


}
