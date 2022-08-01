package com.example.adambackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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


    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<Comment> comments = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<Favorite> favorites = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    List<DetailProduct> detailProducts = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "product_name")
    private String productName;
    private String description;
    @Column(name = "is_deleted")
    private Boolean isDelete;
    private String image;
    @Column(name = "vote_average")
    private Double voteAverage;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "is_completed")
    private Boolean isComplete;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<TagProduct> tagProducts = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<MaterialProduct> materialProducts = new ArrayList<>();
    @Column(name = "is_active")
    private Boolean isActive;
}
