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
@Table(name="categories")
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="category_name")
    private String categoryName;
    @Column(name="is_delete")
    private Boolean isDelete;
    @Column(name="category_parent_id")
    private int categoryParentId;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL)
    List<Product> products= new ArrayList<>();
}
