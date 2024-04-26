package com.example.adambackend.payload.detailProduct;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomDetailProductResponse {
	private List<NewDetailProductDTO> newDetailProductDTOList;
}
