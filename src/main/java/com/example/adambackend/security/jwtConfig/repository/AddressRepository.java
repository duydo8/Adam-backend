package com.example.adambackend.security.jwtConfig.repository;

import com.example.adambackend.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
}
