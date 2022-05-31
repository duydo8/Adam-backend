package com.example.adambackend.service;

import com.example.adambackend.entities.Color;

import java.util.List;
import java.util.Optional;

public interface ColorService {

    Optional<Color> findById(Long id);

    void deleteById(Long id);

    Color save(Color color);

    List<Color> findAll();
}
