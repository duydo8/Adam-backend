package com.example.adambackend.entities;

import com.example.adambackend.payload.address.AddressWebsiteDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "address")
@Entity
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String addressDetail;
	private Integer status;
	private LocalDateTime createDate;
	private String phoneNumber;
	private String fullName;
	private Boolean isDefault;

	@ManyToOne
	@JoinColumn(name = "ward_id")
	private Ward ward;

	@ManyToOne
	@JoinColumn(name = "province_id")
	private Province province;

	@ManyToOne
	@JoinColumn(name = "district_id")
	private District district;

	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@JsonIgnore
	@OneToMany(mappedBy = "address")
	private List<Order> orders = new ArrayList<>();

	public Address(AddressWebsiteDto addressWebsiteDto){
		this.addressDetail = addressWebsiteDto.getAddressDetail();
		this.createDate = LocalDateTime.now();
		this.status = 1;
		this.phoneNumber = addressWebsiteDto.getPhoneNumber();
		this.fullName = addressWebsiteDto.getFullName();
		this.isDefault = addressWebsiteDto.getIsDefault();
	}
}
