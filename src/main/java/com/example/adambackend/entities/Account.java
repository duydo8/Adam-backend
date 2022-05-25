package com.example.adambackend.entities;

import com.example.adambackend.enums.ERoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="accounts")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Column(name="full_name")
    private String fullName;
    private String email;

    private String password;
    private ERoleName role;
    private String photo;
    @Column(name="is_active")
    private boolean isActive;
    @Column(name="is_delete")
    private boolean isDelete;
    private float rate;
    @Column(name = "verification_code")
    private String verificationCode;
    @Column(name="time_valid",columnDefinition = "DATE")
    private LocalDateTime timeValid;
    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    @OneToMany(mappedBy = "account")

    List<Address> addresses = new ArrayList<>();
    @OneToMany(mappedBy = "account")

    List<Comment> comments = new ArrayList<>();
    @OneToMany(mappedBy = "account")

    List<Favorite> favorites = new ArrayList<>();
    @OneToMany(mappedBy = "account")

    List<CartItems> cartItems = new ArrayList<>();
    @OneToMany(mappedBy = "account")
    List<Order> orders = new ArrayList<>();

}
