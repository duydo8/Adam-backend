package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class AccountEventPK implements Serializable {
    private static final long serialVersionUID = -7524270329897682645L;
    @Column(name="account_id")
    private Long accountId;
    @Column(name="event_id")
    private Long eventId;
    @Column(name="code")
    private String code;

}
