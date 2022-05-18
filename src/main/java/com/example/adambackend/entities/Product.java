package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private boolean delete;
    @ManyToOne
    @JoinColumn(name="category_id")
    private Category category;
    private String image;
    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    List<Comment> comments= new ArrayList<>();
}
