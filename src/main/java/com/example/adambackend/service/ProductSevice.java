package com.example.adambackend.service;

import java.util.List;
import java.util.Optional;

import com.example.adambackend.entities.Product;

public interface ProductSevice {

	Optional<Product> findById(Long id);

	void deleteById(Long id);

	Product create(Product Product);

	List<Product> findAll();
}
