package com.example.adambackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "discount_orders")
public class DiscountOrder {
    @JsonIgnore
    @OneToMany(mappedBy = "discountOrder")
    List<Order> orders = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "discount_name")
    private String discountName;
    private String description;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "sale_price")
    private Double salePrice;
    @Column(name = "order_min_range")
    private Double orderMinRange;
    @Column(name = "order_max_range")
    private Double orderMaxRange;
    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;


}
