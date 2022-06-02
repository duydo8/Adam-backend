package com.example.adambackend.entities;

import com.example.adambackend.enums.ERoleName;
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
@Table(name = "accounts")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(name = "full_name")
    private String fullName;
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String password;
    private ERoleName role;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "is_deleted")
    private boolean isDelete;
    private int priority;
            @Column(name = "verification_code")
    private String verificationCode;
    @Column(name = "time_valid")
    private LocalDateTime timeValid;

    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @OneToMany(mappedBy = "account")
    List<Address> addressList = new ArrayList<>();
    @OneToMany(mappedBy = "account")
    List<Comment> commentList = new ArrayList<>();
    @OneToMany(mappedBy = "account")
    List<Favorite> favoriteList = new ArrayList<>();
    @OneToMany(mappedBy = "account")
    List<CartItems> cartItemsList = new ArrayList<>();
    @OneToMany(mappedBy = "account")
    List<Order> orderList = new ArrayList<>();

}
