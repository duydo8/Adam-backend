package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AccountEventPK {
    @Column(name="account_id")
    private Long accountId;
    @Column(name="event_id")
    private Long eventId;
    @Column(name="code")
    private String code;

}
