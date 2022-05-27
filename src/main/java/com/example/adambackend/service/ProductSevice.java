package com.example.adambackend.service;

import java.util.List;
import java.util.Optional;

import com.example.adambackend.entities.Product;
import org.springframework.data.domain.Page;

public interface ProductSevice {

	Optional<Product> findById(Long id);

	void deleteById(Long id);

	Product save(Product product);

	List<Product> findAll();
	Page<Product>  findPage (int page, int size);
}
