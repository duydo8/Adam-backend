package com.example.adambackend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events")
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String eventName;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	private String description;
	private Integer status;
	private LocalDateTime createDate;
	private Boolean type;
	private String image;

	@OneToMany(mappedBy = "event")
	List<DiscountOrder> discountOrders;
}
