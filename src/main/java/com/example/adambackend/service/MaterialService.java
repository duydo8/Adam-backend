package com.example.adambackend.service;

import com.example.adambackend.entities.Material;

import java.util.List;
import java.util.Optional;

public interface MaterialService {
	List<Material> findAll(String name);

	Material save(Material Tag);

	void deleteById(Integer id);

	Optional<Material> findById(Integer id);


	void updateDeletedByListId(List<Integer> materialIs);
}
