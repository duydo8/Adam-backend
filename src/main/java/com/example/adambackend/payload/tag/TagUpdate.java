package com.example.adambackend.payload.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TagUpdate {
	private Integer id;
	private String tagName;
	private Integer status;
}