package com.example.adambackend.controller.admin;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.CartItems;
import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.cart.CartItemCreate;
import com.example.adambackend.payload.cart.CartItemUpdate;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.CartItemService;
import com.example.adambackend.service.DetailProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/cart")
public class CartItemController {

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private DetailProductService detailProductService;

	@PostMapping("create")
	public ResponseEntity<?> create(@RequestBody CartItemCreate cartItemCreate) {
		try {
			Optional<Account> account = accountService.findById(cartItemCreate.getAccountId());
			Optional<DetailProduct> detailProduct = detailProductService.findById(cartItemCreate.getDetailProductId());

			String error = cartItemService.validateCreateCartItem(account,cartItemCreate, detailProduct.get());
			if (CommonUtil.isNotNull(error)) {
				return ResponseEntity.ok().body(new IGenericResponse<>(200, error));
			}
			return ResponseEntity.ok().body(new IGenericResponse<>(cartItemService.createCartItem(account.get(),
					detailProduct.get(), cartItemCreate), 200, "success"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody CartItemUpdate cartItemUpdate) {
		try {
			Optional<CartItems> cartItemsOptional = cartItemService.findById(cartItemUpdate.getId());
			if (cartItemsOptional.isPresent()) {
				CartItems cartItems = cartItemsOptional.get();
				cartItems.setQuantity(cartItemUpdate.getQuantity());
				cartItems.setTotalPrice(cartItemUpdate.getTotalPrice());
				return ResponseEntity.ok().body(new IGenericResponse(cartItemService.save(cartItems), 200, "success"));
			}
			return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
		try {
			Optional<CartItems> cartItemsOptional = cartItemService.findById(id);
			if (cartItemsOptional.isPresent()) {
				cartItemService.deleteById(id);
				return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));
			}
			return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findAll")
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(new IGenericResponse<>(cartItemService.findAll(), 200, "success"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findById")
	public ResponseEntity<?> findById(@RequestParam("id") Integer id) {
		try {
			Optional<CartItems> cartItems = cartItemService.findById(id);
			if (cartItems.isPresent()) {
				return ResponseEntity.ok(new IGenericResponse<>(cartItems.get(), 200, "success"));
			}
			return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findByAccountId")
	public ResponseEntity<?> findByAccountId(@RequestParam("account_id") Integer accountId) {
		try {
			Optional<Account> account = accountService.findById(accountId);
			if (account.isPresent()) {
				return ResponseEntity.ok().body(new IGenericResponse<>(cartItemService
						.findByAccountId(accountId), 200, "success"));
			}
			return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
