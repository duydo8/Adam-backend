package com.example.adambackend.payload.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountAdminDTO {
    private Integer id;
    private String fullName;
    private String email;
    private String password;
    private Boolean isActive;
    private Double priority;


}
