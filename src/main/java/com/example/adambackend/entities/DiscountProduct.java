package com.example.adambackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="discount_products")
public class DiscountProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="discount_name")
    private String discountName;
    private String description;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name="is_active")
    private Boolean isActive;
    @Column(name="is_deleted")
    private Boolean isDeleted;
    @Column(name="sale_price")
    private Double salePrice;
    @JsonIgnore
    @OneToMany(mappedBy ="discountProduct" )
    private List<DetailOrder> detailOrderList;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
