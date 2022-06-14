package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "detail_orders")
@Entity
public class DetailOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int quantity;
    private double price;
    @Column(name="is_deleted")
    private Boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "detail_product_id")
    private DetailProduct detailProduct;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

}
