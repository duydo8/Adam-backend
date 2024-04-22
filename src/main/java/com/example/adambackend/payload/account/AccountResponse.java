package com.example.adambackend.payload.account;


import com.example.adambackend.enums.ERoleName;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class AccountResponse {
	Integer id;
	String username;
	String fullName;
	String email;
	String phoneNumber;
	String role;
	Integer status;
	Double priority;

	public AccountResponse(Integer id, String username, String fullName, String email, String phoneNumber,
	                       ERoleName role, Integer status, Double priority) {
		this.id = id;
		this.username = username;
		this.fullName = fullName;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = String.valueOf(role);
		this.status = status;
		this.priority = priority;
	}
}
