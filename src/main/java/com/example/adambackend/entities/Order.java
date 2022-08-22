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
@Table(name = "orders")
@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer status;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account_id")
    private Account account;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "amount_price")
    private Double amountPrice;
    @Column(name = "sale_price")
    private Double salePrice;
    @Column(name = "total_price")
    private Double totalPrice;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
    @Column(name = "address_detail")
    private String addressDetail;
    @Column(name = "order_code")
    private String orderCode;
    @Column(name = "return_order_price")
    private Double returnOrderPrice = 0.0;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistoryOrder> historyOrders = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<CartItems> cartItems = new ArrayList<>();
    @JsonIgnore
    @OneToMany(mappedBy = "order")
    private List<DetailOrder> detailOrders = new ArrayList<>();
}
