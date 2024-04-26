package com.example.adambackend.payload.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {
	private Integer id;
	private String eventName;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String description;
	private Integer status;
	private LocalDateTime createDate;
	private Boolean type;
	private String image;
	private Double salePrice;
}
