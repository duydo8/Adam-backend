package com.example.adambackend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailProductRequest {
    Integer productId;
    Integer quantity;
    Double priceImport;
    Double priceExport;


    @ElementCollection
    List<Integer> colorIdList;
    @ElementCollection
    List<Integer> sizeIdList;

}
