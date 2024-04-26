package com.example.adambackend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "districts")
public class District {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	private String prefix;

	@ManyToOne
	@JoinColumn(name = "province_id")
	private Province province;

	@JsonIgnore
	@OneToMany(mappedBy = "district")
	private List<Ward> wards = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "district")
	private List<Address> addresses = new ArrayList<>();
}
