package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="material_products")
public class MaterialProduct {
    @EmbeddedId
    private MaterialProductPK materialProductPK;
    @Column(name="is_deleted")
    private Boolean isDeleted;
    @ManyToOne
    @MapsId("materialId")
    @JoinColumn(name = "material_id")
    private Material material = new Material();
    @Column(name="is_active")
    private Boolean isActive;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product = new Product();
    
}
