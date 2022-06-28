package com.example.adambackend.payload.productWebsiteDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductWebstieFilterDTO {
    private List<Integer> listCategoryId;
    private List<Integer> listColorId;
    private List<Integer> listSizeId;
    private List<Integer> listMaterialId;
    private List<Integer> listTagId;
    private Double bottomPrice;
    private Double topPrice;
    private Integer page;
    private Integer size;


}
