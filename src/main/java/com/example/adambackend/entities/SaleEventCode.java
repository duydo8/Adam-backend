package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="sale_event_code")
public class SaleEventCode {
    @Id
    private String code;
    @Column(name="sale_event_name")
    private String SaleEventName;
    private String description;
    private double promotion;
    @OneToMany(mappedBy = "saleEvent")
    private  List<AccountEvent> accountEvents= new ArrayList<>();
}
