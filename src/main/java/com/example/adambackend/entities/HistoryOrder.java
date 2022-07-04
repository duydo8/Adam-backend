package com.example.adambackend.entities;

import com.example.adambackend.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="history_orders")
@Entity
public class HistoryOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer status;
    @Column(name="update_time")
    private LocalDateTime updateTime;
    private String description;
    @Column(name="is_active")
    private Boolean isActive;
    @Column(name="total_price")
    private Double totalPrice;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

}
