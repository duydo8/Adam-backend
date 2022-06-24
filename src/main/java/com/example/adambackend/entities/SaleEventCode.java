package com.example.adambackend.entities;

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
@Table(name = "sale_event_code")
public class SaleEventCode {
    @Id
    private String code;
    @Column(name = "sale_event_name")
    private String SaleEventName;
    private String description;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @JsonIgnore
    @OneToMany(mappedBy = "saleEventCode")
    private List<AccountEvent> accountEvents = new ArrayList<>();
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
}
