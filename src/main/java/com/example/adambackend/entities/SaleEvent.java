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
@Table(name="sale_events")
public class SaleEvent {
    @Id
    private String code;
    @Column(name="sale_event_name")
    private String SaleEventName;
    private String description;
    @OneToMany(mappedBy = "saleEvent")
    private  List<AccountEvent> accountEvents= new ArrayList<>();
}
