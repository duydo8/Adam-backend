package com.example.adambackend.payload.detailOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrderWebsiteUpdate {
	private Integer id;
	private Integer quantity;
	private Double price;
}
