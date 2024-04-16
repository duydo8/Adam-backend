package com.example.adambackend.service.impl;


import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.CartItems;
import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.payload.cart.CartItemCreate;
import com.example.adambackend.payload.cart.CartItemResponse;
import com.example.adambackend.repository.CartItemRepository;
import com.example.adambackend.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService {

	@Autowired
	private CartItemRepository cartItemRepository;

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
	public Optional<CartItems> findByIds(Integer id) {
		return cartItemRepository.findExistOrderById(id);
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

	@Override
	public String validateCreateCartItem(CartItemCreate cartItemCreate, DetailProduct detailProduct) {
		if (cartItemCreate.getQuantity() >= 10) {
			return "Không thể mua số lượng >10";
		}
		if (detailProduct.getQuantity() < cartItemCreate.getQuantity()) {
			return "Không đủ số lượng";
		}
		return null;
	}

	@Override
	public CartItems createCartItem(Account account, DetailProduct detailProduct, CartItemCreate cartItemCreate) {
		List<CartItems> cartItemsList = cartItemRepository.findCartItemsByAccountId(cartItemCreate.getAccountId());
		for (CartItems c : cartItemsList) {
			if (detailProduct.getId() == c.getDetailProduct().getId()) {
				c.setQuantity(c.getQuantity() + 1);
				return c;
			}
		}
		CartItems cartItems = new CartItems(null, cartItemCreate.getQuantity()
				, cartItemCreate.getQuantity() * detailProduct.getPriceExport(),
				1, LocalDateTime.now(), null, account, detailProduct);
		return cartItems;
	}
}
