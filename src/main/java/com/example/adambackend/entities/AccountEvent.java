package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account_events")
public class AccountEvent {
    @EmbeddedId
    private AccountEventPK accountEventPK;
    @Column(name = "time_use")
    private LocalDateTime timeUse;
    @Column(name = "time_valid")
    private LocalDateTime timeValid;
    @Column(name = "is_active")
    private Boolean isActive;
    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account = new Account();
    @Column(name = "is_deleted")
    private Boolean isDeleted;
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event = new Event();
    @ManyToOne
    @MapsId("code")
    @JoinColumn(name = "code")
    private SaleEventCode saleEventCode = new SaleEventCode();


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order = new Order();
}
