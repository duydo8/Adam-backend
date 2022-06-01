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
@Table(name = "tags")
@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tag_name")
    private String tagName;
    @OneToMany(mappedBy = "tag")
    List<Category> categoryList= new ArrayList<>();
    @OneToMany(mappedBy = "tag")
    List<Product> products= new ArrayList<>();
    @OneToMany(mappedBy = "tag")
    List<Event> events= new ArrayList<>();
    @OneToMany(mappedBy = "tag")
    List<Brand> brands= new ArrayList<>();
    @OneToMany(mappedBy = "tag")
    List<Material> materials= new ArrayList<>();
    @OneToMany(mappedBy = "tag")
    List<Discount> discounts=  new ArrayList<>();

}
