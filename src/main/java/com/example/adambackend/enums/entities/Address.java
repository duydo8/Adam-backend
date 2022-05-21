package com.example.adambackend.enums.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="address")
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="address_name")
    private String addressName;
    @Column(name="address_detail")
    private String addressDetail;

    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
}
