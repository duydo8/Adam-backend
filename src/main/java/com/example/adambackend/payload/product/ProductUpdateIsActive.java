package com.example.adambackend.payload.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateIsActive {
    private Integer is_active;
    private Integer id;


}
