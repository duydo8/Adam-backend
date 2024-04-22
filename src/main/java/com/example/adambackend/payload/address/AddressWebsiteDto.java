package com.example.adambackend.payload.address;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressWebsiteDto {
	private Integer id;
	private String addressDetail;
	private Integer accountId;
	private Integer provinceId;
	private Integer districtId;
	private Integer wardId;
	private String phoneNumber;
	private String fullName;
	private Boolean isDefault;
	private Integer status;
}
