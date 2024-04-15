package com.example.adambackend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AccountLoginRequestDto {
	private String username;
	private String password;
}
