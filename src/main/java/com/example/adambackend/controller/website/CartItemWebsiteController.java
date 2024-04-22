package com.example.adambackend.controller.website;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.CartItems;
import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.payload.cart.CartItemCreate;
import com.example.adambackend.payload.cart.CartItemUpdate;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.CartItemService;
import com.example.adambackend.service.DetailProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/user/cart")
public class CartItemWebsiteController {

	@Autowired
	private CartItemService cartItemService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private DetailProductService detailProductService;

	@PostMapping("create")
	public ResponseEntity<?> create(@RequestBody CartItemCreate cartItemCreate) {
		try {
			Optional<Account> accountOptional = accountService.findById(cartItemCreate.getAccountId());
			Optional<DetailProduct> detailProductOptional = detailProductService.findById(cartItemCreate.getDetailProductId());
			String errors = cartItemService.validateCreateCartItem(accountOptional, cartItemCreate, detailProductOptional);
			if (CommonUtil.isNotNull(errors)) {
				return ResponseEntity.badRequest().body(new IGenericResponse(200, errors));
			}
			return ResponseEntity.ok().body(new IGenericResponse<>(cartItemService.createCartItem(accountOptional.get(),
					detailProductOptional.get(), cartItemCreate), 200, "success"));
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
				return ResponseEntity.ok().body(new IGenericResponse(cartItemService.save(cartItems), 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
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
				return ResponseEntity.ok().body(new IGenericResponse(200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findAll")
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(new IGenericResponse(cartItemService.findAll(), 200, "successfully"));
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
				return ResponseEntity.ok(new IGenericResponse(cartItems.get(), 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
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
				return ResponseEntity.ok().body(new IGenericResponse(cartItemService.findByAccountId(accountId), 200, "successfully"));
			}
			return ResponseEntity.ok().body(new IGenericResponse(200, ""));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
