package com.example.adambackend.payload.request;

import com.example.adambackend.enums.ERoleName;
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
}
