package com.example.adambackend.service;

import java.util.List;
import java.util.Optional;

import com.example.adambackend.entities.CartItems;

public interface CartItemService {

	Optional<CartItems> findById(Long id);

	void deleteById(Long id);

	CartItems create(CartItems cartItems);

	List<CartItems> findAll();
}
