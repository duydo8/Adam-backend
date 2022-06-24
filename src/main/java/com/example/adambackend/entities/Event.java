package com.example.adambackend.entities;

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
@Table(name = "events")
public class Event {
    @OneToMany(mappedBy = "event")
    List<AccountEvent> accountEvents = new ArrayList<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name_event")
    private String nameEvent;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    private Boolean status;
    @Column(name = "is_deleted")
    private Boolean isDelete;
    @ManyToOne
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "create_date")
    private LocalDateTime createDate;

}
