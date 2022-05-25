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
@Entity
@Table(name="districts")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String prefix;
    @ManyToOne
    @JoinColumn(name="province_id")
    private Province province;
    @OneToMany(mappedBy = "district")
    private List<Ward> wards= new ArrayList<>();
    @OneToMany(mappedBy = "district")
    private List<Address> addresses= new ArrayList<>();



}
