package com.example.adambackend.service;

import com.example.adambackend.entities.Color;
import com.example.adambackend.payload.color.ColorUpdate;

import java.util.List;
import java.util.Optional;

public interface ColorService {

    Optional<Color> findById(Integer id);

    Color update(ColorUpdate color);

    void deleteById(Integer id);

    Color save(Color color);

    List<Color> findAll(String name);

    Optional<Color> findByDetailProductId(Integer detailProductId);

    String updateColorsDeleted(List<Integer> listColorIdDTO);
}
