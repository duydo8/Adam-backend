package com.example.adambackend.entities;

import com.example.adambackend.enums.ERoleName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String username;
	private String fullName;
	@Column(unique = true)
	private String email;
	@Column(unique = true)
	private String phoneNumber;
	private String password;
	private ERoleName role;
	// 0 : not active, 1 : active, 9 : delete
	private Integer status;
	private Integer verificationCode;
	private LocalDateTime timeValid;
	private Double priority;
	private LocalDateTime createDate;

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Address> addresses = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Comment> commentList = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Favorite> favoriteList;

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<CartItems> cartItemsList = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "account")
	List<Order> orderList = new ArrayList<>();

	public Account(String username, String email, String password, String phoneNumber, String fullName) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.phoneNumber = phoneNumber;
		this.fullName = fullName;
	}

	public Account() {
		this.createDate = LocalDateTime.now();
		this.status = 0;
	}
}
