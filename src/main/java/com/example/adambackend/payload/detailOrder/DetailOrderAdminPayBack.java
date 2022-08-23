package com.example.adambackend.payload.detailOrder;

import com.example.adambackend.entities.DetailProduct;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrderAdminPayBack {
    private String detailOrderCode;
    private Integer quantity;


}
