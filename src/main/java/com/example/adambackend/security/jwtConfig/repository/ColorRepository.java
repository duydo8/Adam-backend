package com.example.adambackend.security.jwtConfig.repository;

import com.example.adambackend.entities.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
}
