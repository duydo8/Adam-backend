package com.example.adambackend.enums.entities;

import com.example.adambackend.enums.ERoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    private String email;

    private String password;
    private ERoleName roleName;

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
