package com.example.adambackend.payload.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountAdminCreate {
	private String username;
	private String email;
	private String role;
	private String password;
	private String phoneNumber;
	private String fullName;
}
