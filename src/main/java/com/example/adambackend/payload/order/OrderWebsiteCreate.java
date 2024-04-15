package com.example.adambackend.payload.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderWebsiteCreate {
	private String fullName;
	private String phoneNumber;
	private String addressDetail;
	private Integer addressId;
	private Integer accountId;
	private List<Integer> cartItemIdList;
}
