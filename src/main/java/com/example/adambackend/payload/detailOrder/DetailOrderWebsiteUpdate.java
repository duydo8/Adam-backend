package com.example.adambackend.payload.detailOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrderWebsiteUpdate {
    private Integer id;
    private Integer quantity;
    private Double price;


}
