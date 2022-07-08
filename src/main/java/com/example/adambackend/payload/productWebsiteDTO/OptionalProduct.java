package com.example.adambackend.payload.productWebsiteDTO;

import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.Material;
import com.example.adambackend.entities.Size;
import com.example.adambackend.entities.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionalProduct {
    private Set<Tag> tagList;
    private Set<Material> materialList;
    private Set<Color> colorList;
    private Set<Size> sizeList;
    private String description;
}
