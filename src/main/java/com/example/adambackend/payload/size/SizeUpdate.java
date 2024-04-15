package com.example.adambackend.payload.size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SizeUpdate {
	private Integer id;
	private String sizeName;
	private Integer status;
}
