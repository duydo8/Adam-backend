package com.example.adambackend.service.impl;


import com.example.adambackend.entities.CartItems;
import com.example.adambackend.repository.CartItemRepository;
import com.example.adambackend.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartItemRepository CartItemRepository;
    @Override
    public List<CartItems> findAll() {
        return CartItemRepository.findAll();
    }

    @Override
    public CartItems create(CartItems CartItem) {
        return CartItemRepository.save(CartItem);
    }

    @Override
    public void deleteById(Long id) {
        CartItemRepository.deleteById(id);
    }

    @Override
    public Optional<CartItems> findById(Long id) {
        return CartItemRepository.findById(id);
    }
}
