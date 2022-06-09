package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TagProductPK {
    @Column(name = "tag_id")
    private Long tagId;
    @Column(name = "product_id")
    private Long productId;

}

