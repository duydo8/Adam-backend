package com.example.adambackend.payload;

import com.example.adambackend.entities.CartItems;
import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

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
