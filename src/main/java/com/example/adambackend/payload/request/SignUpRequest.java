package com.example.adambackend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {
    private String username;
    private String email;
    private String role;
    private String password;
    private String phoneNumber;
    private String fullName;
    private int code;
}
