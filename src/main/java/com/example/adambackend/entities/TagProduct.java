package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="tag_products")
public class TagProduct {
    @EmbeddedId
    private TagProductPK tagProductPK;
    @Column(name="is_deleted")
    private Boolean isDeleted;
    @ManyToOne
    @MapsId("tagId")
    @JoinColumn(name = "tag_id")
    private Tag tag = new Tag();

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product = new Product();

}
