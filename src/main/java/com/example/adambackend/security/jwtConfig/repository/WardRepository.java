package com.example.adambackend.security.jwtConfig.repository;

import com.example.adambackend.entities.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> {
}
