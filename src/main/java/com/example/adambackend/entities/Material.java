package com.example.adambackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "materials")
public class Material {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String materialName;
	private Integer status;
	private LocalDateTime createDate;

	@JsonIgnore
	@OneToMany(mappedBy = "material")
	private List<MaterialProduct> materialProducts = new ArrayList<>();
}
