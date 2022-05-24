package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private String description;
    private boolean isDelete;
    private String image;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    List<Comment> comments= new ArrayList<>();
    @OneToMany(mappedBy = "product")
    List<Favorite> favorites= new ArrayList<>();
    @OneToMany(mappedBy = "product")
    List<DetailProduct> detailProducts= new ArrayList<>();

}
