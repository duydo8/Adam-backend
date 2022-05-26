package com.example.adambackend.service;

import java.util.List;
import java.util.Optional;

import com.example.adambackend.entities.Color;

public interface ColorService {

	Optional<Color> findById(Long id);

	void deleteById(Long id);

	Color create(Color color);

	List<Color> findAll();
}
