package com.example.adambackend.enums.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detail_order")
@Entity
public class DetailOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private double price;
    @ManyToOne
    @JoinColumn(name = "detail_product_id")
    private DetailProduct detailProduct;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
