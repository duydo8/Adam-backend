package com.example.adambackend.payload.account;

public interface AccountResponse {
    Integer getId();

    String getUsername();

    String getFullName();

    String getEmail();

    String getPhoneNumber();

    String getPassword();

    String getRole();

    Boolean getIsActive();

    Boolean getIsDelete();

    Double getPriority();


}
