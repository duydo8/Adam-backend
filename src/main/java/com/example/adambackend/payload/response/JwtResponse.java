package com.example.adambackend.payload.response;

import com.example.adambackend.enums.ERoleName;
import lombok.Data;

@Data

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private String roles;


    public JwtResponse(String accessToken, Long id, String username, String email, String roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}

