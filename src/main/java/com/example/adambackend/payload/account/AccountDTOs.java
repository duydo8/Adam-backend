package com.example.adambackend.payload.account;

import com.example.adambackend.enums.ERoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

public interface AccountDTOs {
     Integer getId();
     String getUsername();
     String getFullName();
     String getEmail();
     String getPhoneNumber();
}
