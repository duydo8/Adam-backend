package com.example.adambackend.entities;

import com.example.adambackend.enums.ERoleName;
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
@Table(name = "accounts")

public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String username;
    @Column(name = "full_name")
    private String fullName;
    @Column(unique = true)
    private String email;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    private String password;
    private ERoleName role;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "is_deleted")
    private Boolean isDelete;
    @Column(name = "verification_code")
    private Integer verificationCode;
    @Column(name = "time_valid")
    private LocalDateTime timeValid;
    private Double priority;
    @Column(name = "create_date")
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
}
