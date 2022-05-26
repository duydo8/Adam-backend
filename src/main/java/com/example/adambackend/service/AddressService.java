package com.example.adambackend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.adambackend.entities.Address;
import com.example.adambackend.repository.AddressRepository;

public interface AddressService {

	Optional<Address> findById(Long id);

	void deleteById(Long id);

	Address create(Address address);

	List<Address> findAll();

}
