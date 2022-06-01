package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="materials")
public class Material {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="material_name")
    private String materialName;
    @OneToMany(mappedBy = "material")
    List<Product> products= new ArrayList<>();
    @ManyToOne
    @JoinColumn(name="tag_id")
    private Tag tag;
}
