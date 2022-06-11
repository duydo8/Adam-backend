package com.example.adambackend.service;

import com.example.adambackend.entities.Material;

import java.util.List;
import java.util.Optional;

public interface MaterialService {
    List<Material> findAll();

    Material save(Material Tag);

    void deleteById(Long id);

    Optional<Material> findById(Long id);
}
