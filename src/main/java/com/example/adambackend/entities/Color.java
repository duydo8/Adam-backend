package com.example.adambackend.entities;

import com.example.adambackend.payload.color.ColorDTO;
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
@Table(name = "colors")
@Entity
public class Color {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "color_name")
	private String colorName;
	private Integer status;
	@Column(name = "create_date")
	private LocalDateTime createDate;

	@JsonIgnore
	@OneToMany(mappedBy = "color", cascade = CascadeType.ALL)
	private List<DetailProduct> detailProducts = new ArrayList<>();

	public Color(ColorDTO colorDTO){
		this.colorName = colorDTO.getColorName();
		this.status = 1;
		this.createDate = LocalDateTime.now();
	}
}
