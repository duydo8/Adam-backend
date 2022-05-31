package com.example.adambackend.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailProductDto {
    private Long id;
    private int quantity;
    private Double price;
    private Boolean isDelete;
    private String productImage;
    private String productName;
    private String colorName;
    private String sizeName;
}
