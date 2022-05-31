package com.example.adambackend.service;

import com.example.adambackend.entities.Address;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    Optional<Address> findById(Long id);

    void deleteById(Long id);

    Address save(Address address);

    List<Address> findAll();

}
