package com.example.adambackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "event_name")
    private String eventName;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    private String description;
    @Column(name = "is_deleted")
    private Boolean isDelete;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    private Boolean type;
    private String image;
    @JsonIgnore
    @OneToMany(mappedBy = "event")
    List<DiscountProduct> discountProducts;
    @JsonIgnore
    @OneToMany(mappedBy = "event")
    List<DiscountOrder> discountOrders;
}
