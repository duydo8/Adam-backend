package com.example.adambackend.enums.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "history_order")
public class HistoryOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "date_create")
    private LocalDateTime dateCreate;
    private String description;
    @OneToMany(mappedBy = "historyOrder")
    private List<Order> orderList;
}
