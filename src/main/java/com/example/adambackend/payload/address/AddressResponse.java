package com.example.adambackend.payload.address;

import com.example.adambackend.entities.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public interface AddressResponse {
     Integer getId();
     String getAddressDetail();
     Boolean getIsDeleted();
     LocalDateTime getCreateDate();
     Ward getWard();
     Boolean getIsActive();
     String getPhoneNumber();
     String getFullName();
     Boolean getIsDefault();
     Province getProvince();
     District getDistrict();
}
