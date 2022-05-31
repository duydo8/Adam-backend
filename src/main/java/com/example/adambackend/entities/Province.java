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
@Table(name = "provinces")
public class Province {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "province")
    private List<District> districts = new ArrayList<>();
    @OneToMany(mappedBy = "province")
    private List<Ward> wards = new ArrayList<>();
    @OneToMany(mappedBy = "province")
    private List<Address> addresses = new ArrayList<>();


}
