package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Address;
import com.example.adambackend.entities.Order;
import com.example.adambackend.payload.order.OrderWebsiteCreate;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.AddressService;
import com.example.adambackend.service.CartItemService;
import com.example.adambackend.service.DetailOrderService;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.DiscountOrderService;
import com.example.adambackend.service.EventService;
import com.example.adambackend.service.HistoryOrderService;
import com.example.adambackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("user/order")
public class OrderWebsiteController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private DetailOrderService detailOrderService;

	@Autowired
	private AddressService addressService;

	@PostMapping("create")
	public ResponseEntity<?> createOrder(@RequestBody OrderWebsiteCreate orderWebsiteCreate) {
		try {
			Optional<Account> account = accountService.findById(orderWebsiteCreate.getAccountId());
			Optional<Address> address = addressService.findById(orderWebsiteCreate.getAddressId());
			if (address.isPresent() && account.isPresent()) {
				return ResponseEntity.ok().body(new IGenericResponse(orderService.createOder(orderWebsiteCreate, account.get(), address.get()), 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found "));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> deleteOrder(@RequestParam("order_id") Integer orderId) {
		try {
			Optional<Order> optionalOrder = orderService.findById(orderId);
			if (optionalOrder.isPresent()) {
				detailOrderService.deleteAllByOrderId(orderId);
				orderService.deleteById(orderId);
				return ResponseEntity.ok().body(new IGenericResponse(200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found Order"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findById")
	public ResponseEntity<?> findById(@RequestParam("id") Integer id) {
		try {
			Optional<Order> order = orderService.findById(id);
			if (order.isPresent()) {
				return ResponseEntity.ok(new IGenericResponse<>(order.get(), 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findByAccountId")
	public ResponseEntity<?> findByAccountId(@RequestParam("account_id") Integer accountId, @RequestParam("status") Integer status) {
		try {
			Optional<Account> account = accountService.findById(accountId);
			if (account.isPresent()) {
				return ResponseEntity.ok().body(new IGenericResponse<>(orderService.findByAccountId(accountId, status), 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
