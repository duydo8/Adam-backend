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
@Table(name="account_events")
public class AccountEvent {
    @EmbeddedId
    private AccountEventPK accountEventPK;
    @Column(name="time_valid")
    private LocalDateTime timeValid;
    @ManyToOne
    @MapsId("accountId")
    @JoinColumn(name = "account_id")
    private Account account = new Account();

    @ManyToOne
    @MapsId("eventId")
    @JoinColumn(name = "event_id")
    private Event event = new Event();
    @ManyToOne
    @MapsId("code")
    @JoinColumn(name = "code")
    private SaleEvent saleEvent = new SaleEvent();
}
