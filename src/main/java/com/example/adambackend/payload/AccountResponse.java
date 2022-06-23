package com.example.adambackend.payload;

import com.example.adambackend.enums.ERoleName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

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
