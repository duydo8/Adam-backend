package com.example.adambackend.payload.response;

import com.example.adambackend.enums.ERoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    private Long id;
    private String username;
    private String fullName;
    private String email;
    private String password;
    private ERoleName role;
    private boolean isActive;
    private boolean isDelete;
    private float rate;
    private String verificationCode;
    private LocalDateTime timeValid;
}
