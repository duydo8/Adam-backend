package com.example.adambackend.service;

import com.example.adambackend.entities.Province;

import java.util.List;
import java.util.Optional;

public interface ProvinceService {
    List<Province> findAll();

    Province save(Province province);

    void deleteById(Long id);

    Optional<Province> findById(Long id);
}
