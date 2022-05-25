package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double discount;
    @ManyToOne
    @JoinColumn(name="detail_product_id")
    private DetailProduct detailProduct;
    @ManyToOne
    @JoinColumn(name="event_id")
    private Event event;

}
