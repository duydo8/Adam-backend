package com.example.adambackend.entities;

import com.example.adambackend.enums.OrderStatus;
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
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "create_date")
    private LocalDateTime createDate;
    private OrderStatus status;

    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<DetailOrder> detailOrders = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<AccountEvent> accountEventList= new ArrayList<>();

}
