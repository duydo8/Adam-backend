package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Address;
import com.example.adambackend.entities.Order;
import com.example.adambackend.payload.order.OrderAdmin;
import com.example.adambackend.payload.order.OrderReturn;
import com.example.adambackend.payload.order.OrderUpdatePayBack;
import com.example.adambackend.payload.order.OrderWebsiteCreate;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.AddressService;
import com.example.adambackend.service.DetailOrderService;
import com.example.adambackend.service.OrderService;
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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin/order")
public class OrderController {

	private final List<String> months = Arrays.asList("January", "February", "March", "April", "May",
			"June", "July", "August", "September", "October", "November", "December");

	@Autowired
	private OrderService orderService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private DetailOrderService detailOrderService;

	@Autowired
	private AddressService addressService;

	@GetMapping("findAllByPageble")
	public ResponseEntity<?> findAllByPageble(@RequestParam(value = "status", required = false)
											  Integer status,
											  @RequestParam("page") Integer page,
											  @RequestParam("size") Integer size) {
		try {
			boolean checkError = orderService.getErrorsFindAll(page, size);
			if (!checkError) {
				return ResponseEntity.ok().body(new IGenericResponse<>(400, "invalid page or size"));
			}
			OrderAdmin orderAdmin = orderService.findAllOrderAdmin(page, size, status);
			return ResponseEntity.ok().body(new IGenericResponse<>(orderAdmin, 200, "Page Order"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("updateByIdAndStatus")
	public ResponseEntity<?> update(@RequestParam("order_id") Integer orderId,
									@RequestParam("status") Integer status) {
		try {
			return ResponseEntity.ok().body(orderService.updateStatusOrder(orderId, status));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PostMapping("updateReturnOrder")
	public ResponseEntity<?> updateReturnOrder(@RequestBody OrderReturn orderReturn) {
		try {
			return ResponseEntity.ok().body(orderService.getOrderReturn(orderReturn));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("updateOrderPayBack")
	public ResponseEntity<?> updateOrderPayBack(@RequestBody OrderUpdatePayBack orderUpdatePayBack) {
		return ResponseEntity.ok().body(orderService.updateOrderPayBack(orderUpdatePayBack));
	}

	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody Order order) {
		try {
			Optional<Order> orderOptional = orderService.findById(order.getId());
			if (orderOptional.isPresent()) {
				return ResponseEntity.ok().body(new IGenericResponse(orderService.updateOrder(order), 200, "successfully"));
			} else {
				return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found Order"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("updateSuccess")
	public ResponseEntity<?> updateEventSuccess(@RequestParam("orderId") Integer orderId) {
		try {
			Optional<Order> orderOptional = orderService.findById(orderId);
			if (orderOptional.isPresent()) {
				ResponseEntity.ok().body(new IGenericResponse<>(orderService.updateSuccess(orderOptional.get()), 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi... "));
		}
	}


	@GetMapping("orderSatistic")
	public ResponseEntity<?> sumTotalPriceByTime() {
		try {
			return ResponseEntity.ok().body(new IGenericResponse<>(orderService.getOrderSatistic(months), 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}


	@PostMapping("create")
	public ResponseEntity<?> createOrder(@RequestBody OrderWebsiteCreate orderWebsiteCreate) {
		try {
			Optional<Account> account = accountService.findById(orderWebsiteCreate.getAccountId());
			Optional<Address> address = addressService.findById(orderWebsiteCreate.getAddressId());
			if (address.isPresent() && account.isPresent()) {
				return ResponseEntity.ok().body(new IGenericResponse(orderService.createOder(orderWebsiteCreate, account.get(), address.get()), 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Không tìm thấy "));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findSalePrice")
	public ResponseEntity<?> findSalePrice(@RequestParam("amount_price") Double ammountPrice) {
		return ResponseEntity.ok().body(new IGenericResponse<>(orderService.getSalePrice(ammountPrice), 200, "thành công"));
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
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Oops! Lại lỗi api rồi..."));
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
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findByAccountId")
	public ResponseEntity<?> findByAccountId(@RequestParam("account_id") Integer accountId, @RequestParam("status") Integer status) {
		try {
			Optional<Account> account = accountService.findById(accountId);
			if (account.isPresent()) {
				return ResponseEntity.ok().body(new IGenericResponse<>(orderService.findOrderByAccountId(accountId, status), 200, ""));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Không tìm thấy"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Oops! Lại lỗi api rồi..."));
		}
	}
}