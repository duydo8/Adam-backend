package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class TagProductPK implements Serializable {
    @Column(name = "tag_id")
    private Integer tagId;
    @Column(name = "product_id")
    private Integer productId;

}

