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
    @Table(name="detail_order")
    @Entity
    public class DetailOrder {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private int quantity;
        private double price;
        @OneToMany(mappedBy = "detailOrder")

        List<DetailProduct> detailProducts= new ArrayList<>();
}
