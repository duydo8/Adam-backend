package com.example.adambackend.payload.response;

import com.example.adambackend.entities.CartItems;
import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Size;
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
