package com.example.adambackend.payload.address;

import com.example.adambackend.entities.District;
import com.example.adambackend.entities.Province;
import com.example.adambackend.entities.Ward;

import java.time.LocalDateTime;


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
