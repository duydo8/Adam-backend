package com.example.adambackend.enums;

public enum ERoleName {
	Admin(0), User(1);
	private int value;

	private ERoleName(int value) {
		this.value = value;
	}
}
