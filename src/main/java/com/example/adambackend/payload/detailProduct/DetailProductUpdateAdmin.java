package com.example.adambackend.payload.detailProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailProductUpdateAdmin {
	private Integer id;
	private Integer quantity;
	private Double priceImport;
	private Double priceExport;
	private Boolean isDelete;
	private String productImage;
	private Boolean isActive;
	private Integer colorId;
	private Integer sizeId;
}
