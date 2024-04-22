package com.example.adambackend.service;

import com.example.adambackend.entities.Address;
import com.example.adambackend.entities.District;
import com.example.adambackend.entities.Province;
import com.example.adambackend.entities.Ward;
import com.example.adambackend.payload.address.AddressWebsiteDto;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    Optional<Address> findById(Integer id);

    void deleteById(Integer id);

    Address save(Address address);

    List<Address> findAll();

    List<Address> findByAccountId(Integer accountId);

    Address findByAddressId(Integer id);

    Address updateAddress(Address address, Province province, District district, Ward ward, AddressWebsiteDto addressWebsiteDto);
}
