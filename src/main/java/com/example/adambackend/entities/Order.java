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
	private LocalDateTime createDate;
	private String fullName;
	private String phoneNumber;
	private Double amountPrice;
	private Double salePrice;
	private Double totalPrice;
	private String addressDetail;
	private String orderCode;
	private Double returnOrderPrice = 0.0;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<HistoryOrder> historyOrders = new ArrayList<>();

	@OneToMany(mappedBy = "order")
	private List<CartItems> cartItems = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "order")
	private List<DetailOrder> detailOrders = new ArrayList<>();

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "account_id")
	private Account account;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;
}
