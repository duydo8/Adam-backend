package com.example.adambackend.payload.productWebsiteDTO;

import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.Material;
import com.example.adambackend.entities.Size;
import com.example.adambackend.entities.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionalProduct {
    private List<Tag> tagList;
    private List<Material> materialList;
    private List<Color> colorList;
    private List<Size> sizeList;
}
