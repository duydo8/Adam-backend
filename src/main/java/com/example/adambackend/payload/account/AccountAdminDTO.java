package com.example.adambackend.payload.account;

import com.example.adambackend.enums.ERoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountAdminDTO {
    private Integer id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String password;
    private String role;
    private Boolean isActive;
    private Boolean isDelete;
    private Double priority;


}
