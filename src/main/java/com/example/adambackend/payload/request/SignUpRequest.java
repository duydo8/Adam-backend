package com.example.adambackend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
public class SignUpRequest {
    private String username;
    private String email;
    private Set<String> role;
    private String password;

}
