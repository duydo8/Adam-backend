package com.example.adambackend.service;

import com.example.adambackend.entities.District;

import java.util.List;
import java.util.Optional;

public interface DistrictService {
    List<District> findAll();

    District save(District district);

    void deleteById(Long id);

    Optional<District> findById(Long id);
}
