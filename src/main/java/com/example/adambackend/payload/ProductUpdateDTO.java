package com.example.adambackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateDTO {
    private Integer id;
    private String productName;
    private String description;
    private Boolean isDelete;
    private String image;
    private Double voteAverage;
    private LocalDateTime createDate;
    private Integer categoryId;
    private List<Integer> tagProductIds = new ArrayList<>();
    private List<Integer> materialProductIds = new ArrayList<>();
    private Boolean isActive;
}
