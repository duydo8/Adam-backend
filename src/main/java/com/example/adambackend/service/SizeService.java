package com.example.adambackend.service;

import java.util.List;
import java.util.Optional;

import com.example.adambackend.entities.Size;

public interface SizeService {

	Optional<Size> findById(Long id);

	void deleteById(Long id);

	Size save(Size size);

	List<Size> findAll();
}
