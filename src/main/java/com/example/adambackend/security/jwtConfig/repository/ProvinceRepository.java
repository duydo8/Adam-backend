package com.example.adambackend.security.jwtConfig.repository;

import com.example.adambackend.entities.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Integer> {
}
