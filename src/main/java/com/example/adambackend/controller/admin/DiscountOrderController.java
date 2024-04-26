package com.example.adambackend.controller.admin;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.DiscountOrder;
import com.example.adambackend.entities.Event;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.discountOrder.DiscountOrderDTO;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.DiscountOrderService;
import com.example.adambackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin/discountOrder")
public class DiscountOrderController {
	@Autowired
	private DiscountOrderService discountOrderService;

	@Autowired
	private EventService eventService;

	@GetMapping("findAll")
	public ResponseEntity<?> findAll(@RequestParam(value = "name", required = false) String name) {
		try {
			return ResponseEntity.ok().body(new IGenericResponse<>(discountOrderService.findAll(name), 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findById")
	public ResponseEntity<?> findById(@RequestParam("id") Integer id) {
		try {
			Optional<DiscountOrder> discountOrderOptional = discountOrderService.findById(id);
			if (discountOrderOptional.isPresent()) {
				return ResponseEntity.ok(new IGenericResponse<>(discountOrderOptional.get(), 200, "successfully"));
			}
			return ResponseEntity.ok().body(new HandleExceptionDemo(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findByEventId")
	public ResponseEntity<?> findByEventId(@RequestParam("event_id") Integer eventId) {
		try {
			Optional<Event> eventOptional = eventService.findById(eventId);
			if (eventOptional.isPresent()) {
				List<DiscountOrder> discountOrders = discountOrderService.findByEventId(eventId);
				return ResponseEntity.ok().body(new IGenericResponse<>(discountOrders, 200, "successfully"));
			}
			return ResponseEntity.ok().body(new HandleExceptionDemo(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PostMapping("create")
	public ResponseEntity<?> create(@RequestBody DiscountOrderDTO discountOrderDTO) {
		try {
			Event event = eventService.findExistById(discountOrderDTO.getEventId());
			String errors = discountOrderService.validateCreateDisccountOrder(event, discountOrderDTO);
			if (CommonUtil.isNotNull(errors)) {
				return ResponseEntity.ok().body(new IGenericResponse(400, errors));
			}

			DiscountOrder discountOrder = discountOrderService.createDiscountOrder(event, discountOrderDTO);
			if (CommonUtil.isNotNull(discountOrder)) {
				return ResponseEntity.ok().body(new IGenericResponse(discountOrder, 200, "successfully"));
			}
			return ResponseEntity.ok().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody DiscountOrderDTO discountOrderUpdate) {
		try {
			Optional<DiscountOrder> discountOrderOptional = discountOrderService.findById(discountOrderUpdate.getId());
			if (discountOrderOptional.isPresent()) {
				Event event = discountOrderOptional.get().getEvent();
				String errors = discountOrderService.validateCreateDisccountOrder(event, discountOrderUpdate);
				if (CommonUtil.isNotNull(errors)) {
					return ResponseEntity.ok().body(new IGenericResponse(400, errors));
				}
				discountOrderService.updateDiscountOrder(discountOrderOptional.get(), discountOrderUpdate);
				return ResponseEntity.ok().body(new HandleExceptionDemo(200, "successfully"));
			}
			return ResponseEntity.ok().body(new HandleExceptionDemo(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("updateIsActive")
	public ResponseEntity<?> updateIsActive(@RequestParam("id") Integer id) {
		try {
			discountOrderService.updateStatusById(0, id);
			return ResponseEntity.ok().body(new IGenericResponse<>("", 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
