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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name_event")
    private String nameEvent;
    @Column(name = "start_time")
    private LocalDateTime startTime;
    @Column(name = "end_time")
    private LocalDateTime endTime;
    private Boolean status;
    @Column(name = "is_delete")
    private Boolean isDelete;
    @OneToMany(mappedBy = "event")
    private List<Discount> discounts = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name="tag_id")
    private Tag tag;
}
