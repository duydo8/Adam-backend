package com.example.adambackend.entities;

import com.example.adambackend.service.SizeService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "sizes")
@Entity
public class Size {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String sizeName;
	private Integer status;

	@JsonIgnore
	@OneToMany(mappedBy = "size")
	private List<DetailProduct> detailProducts = new ArrayList<>();

	@Column(name = "create_date")
	private LocalDateTime createDate;

	public Size(){
		this.createDate = LocalDateTime.now();
		this.status = 1;
	}
}
