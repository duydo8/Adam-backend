package com.example.adambackend.payload.detailOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrderDTOResponse {
	Integer id;
	Integer quantity;
	Double price;
	Double totalPrice;
	Integer status;
	String detailOrderCode;
	LocalDateTime createDate;
	String reason;
	Integer orderId;
}
