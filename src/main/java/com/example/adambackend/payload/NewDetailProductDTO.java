package com.example.adambackend.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewDetailProductDTO {
    private Integer id;

    private Double priceImport;
    private Double priceExport;
    private String image;
    private Integer quantity;
    private Boolean isActive;

}
