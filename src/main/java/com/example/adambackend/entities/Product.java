package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_name")
    private String productName;
    private String description;
    @Column(name = "is_delete")
    private boolean isDelete;
    private String image;
    private String brand;
    private String material;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @OneToMany(mappedBy = "product")
    List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    List<Favorite> favorites = new ArrayList<>();
    @OneToMany(mappedBy = "product")
    List<DetailProduct> detailProducts = new ArrayList<>();

}
