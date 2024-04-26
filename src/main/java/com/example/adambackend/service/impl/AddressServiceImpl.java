package com.example.adambackend.service.impl;

import com.example.adambackend.entities.Address;
import com.example.adambackend.entities.District;
import com.example.adambackend.entities.Province;
import com.example.adambackend.entities.Ward;
import com.example.adambackend.payload.address.AddressWebsiteDto;
import com.example.adambackend.repository.AddressRepository;
import com.example.adambackend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressRepository addressRepository;

	@Override
	public List<Address> findAll() {
		return addressRepository.findAll();
	}

	@Override
	public Address save(Address Address) {
		return addressRepository.save(Address);
	}

	@Override
	public void deleteById(Integer id) {
		addressRepository.deleteById(id);
	}

	@Override
	public Optional<Address> findById(Integer id) {
		return addressRepository.findById(id);
	}

	@Override
	public List<Address> findByAccountId(Integer accountId) {
		return addressRepository.findByAccountId(accountId);
	}

	@Override
	public Address findByAddressId(Integer id) {
		Optional<Address> address = addressRepository.findById(id);
		if (address.isPresent()) {
			return address.get();
		}
		return null;
	}

	@Override
	public Address updateAddress(Address address, Province province, District district, Ward ward, AddressWebsiteDto addressWebsiteDto) {
		address.setAddressDetail(addressWebsiteDto.getAddressDetail());
		address.setProvince(province);
		address.setDistrict(district);
		address.setWard(ward);
		address.setPhoneNumber(addressWebsiteDto.getPhoneNumber());
		address.setFullName(addressWebsiteDto.getFullName());
		address.setStatus(addressWebsiteDto.getStatus());
		address.setIsDefault(addressWebsiteDto.getIsDefault());
		return addressRepository.save(address);
	}
}
