package com.example.adambackend.entities;

import com.example.adambackend.enums.OrderStatus;
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
@Table(name="orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    @Column(name = "address_detail")
    private String addressDetail;
    @Column(name = "time_create")
    private LocalDateTime timeCreate;
    private OrderStatus status;

    @Column(name="full_name")
    private String fullName;
    @Column(name="phone_number")
    private String phoneNumber;
    @ManyToOne
    @JoinColumn(name = "history_order_id")
    private HistoryOrder historyOrder;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @OneToMany(mappedBy = "order")
    private List<DetailOrder> detailOrders = new ArrayList<>();


}
