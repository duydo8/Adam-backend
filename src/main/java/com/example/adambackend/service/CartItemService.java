package com.example.adambackend.service;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.CartItems;
import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.payload.cart.CartItemCreate;
import com.example.adambackend.payload.cart.CartItemResponse;

import java.util.List;
import java.util.Optional;

public interface CartItemService {

	Optional<CartItems> findById(Integer id);

	Optional<CartItems> findByIds(Integer id);

	List<CartItemResponse> findByOrderId(Integer orderId);

	void deleteById(Integer id);

	CartItems save(CartItems cartItems);

	List<CartItems> findAll();

	List<CartItems> findByAccountId(Integer accountId);

	void updateStatus(Integer status, Integer id);

	String validateCreateCartItem(Optional<Account> account, CartItemCreate cartItemCreate, Optional<DetailProduct> detailProduct);

	CartItems createCartItem(Account account, DetailProduct detailProduct, CartItemCreate cartItemCreate);
}
