package com.example.adambackend.payload.detailProduct;

import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailProductUpdateAdmin {
    private Integer id;
    private int quantity;
    private Double priceImport;
    private Double priceExport;
    private Boolean isDelete;
    private String productImage;
    private Integer productId;
    private Boolean isActive;
    private Integer colorId;
    private Integer sizeId;
}
