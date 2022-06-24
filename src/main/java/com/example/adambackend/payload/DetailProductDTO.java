package com.example.adambackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailProductDTO {
    private int quantity;
    private Double priceImport;
    private Double priceExport;
    private String productImage;
    private Integer productId;
    private Integer colorId;
    private Integer sizeId;
}
