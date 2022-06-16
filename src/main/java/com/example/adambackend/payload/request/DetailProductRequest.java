package com.example.adambackend.payload.request;

import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.ElementCollection;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailProductRequest {
    int productId;
    int quantity;
    double priceImport;
    double priceExport;
    @ElementCollection
    List<Color> colorList;
    @ElementCollection
    List<Size> sizeList;

}
