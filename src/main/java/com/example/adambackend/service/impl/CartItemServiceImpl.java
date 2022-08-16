package com.example.adambackend.service.impl;


import com.example.adambackend.entities.CartItems;
import com.example.adambackend.payload.cart.CartItemResponse;
import com.example.adambackend.repository.CartItemRepository;
import com.example.adambackend.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    CartItemRepository cartItemRepository;

    @Override
    public List<CartItems> findAll() {
        return cartItemRepository.findAll();
    }

    @Override
    public CartItems save(CartItems CartItem) {
        return cartItemRepository.save(CartItem);
    }

    @Override
    public void deleteById(Integer id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public Optional<CartItems> findById(Integer id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public List<CartItemResponse> findByOrderId(Integer orderId) {
        return cartItemRepository.findByOrderId(orderId);
    }

    @Override
    public List<CartItems> findByAccountId(Integer accountId) {
        return cartItemRepository.findCartItemsByAccountId(accountId);
    }

    @Override
    public void updateIsActive(Integer id) {
        cartItemRepository.updateIsActive(id);
    }
}
