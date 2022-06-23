package com.example.adambackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "colors")
@Entity
public class Color {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "color_name")
    private String colorName;
    @Column(name="is_deleted")
    private Boolean isDeleted;
    @JsonIgnore
    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
    private List<DetailProduct> detailProducts = new ArrayList<>();
    @Column(name="is_active")
    private Boolean isActive;
}
