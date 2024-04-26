package com.example.adambackend.payload.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderFindAll {
	private Integer id;
	private Integer status;
	private LocalDateTime createDate;
	private Integer accountId;
	private String fullName;
	private String phoneNumber;
	private Double amountPrice;
	private Double salePrice;
	private Double totalPrice;
	private Integer addressId;
	private String addressDetail;
	private String orderCode;
}
