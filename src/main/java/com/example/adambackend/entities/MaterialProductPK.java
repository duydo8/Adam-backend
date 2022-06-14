package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MaterialProductPK implements Serializable {
    @Column(name = "material_id")
    private Integer materialId;
    @Column(name = "product_id")
    private Integer productId;
}
