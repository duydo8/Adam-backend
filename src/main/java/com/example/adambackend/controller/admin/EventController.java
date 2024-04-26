package com.example.adambackend.controller.admin;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Event;
import com.example.adambackend.payload.event.EventDTO;
import com.example.adambackend.payload.event.ListEventId;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.DiscountOrderService;
import com.example.adambackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@RequestMapping("/admin/event")
public class EventController {

	@Autowired
	private EventService eventService;

	@Autowired
	private DiscountOrderService discountOrderService;

	@PostMapping("create")
	public ResponseEntity<?> createEvent(@RequestBody EventDTO eventDTO) {
		try {
			return ResponseEntity.ok().body(new IGenericResponse(eventService.createEvent(eventDTO), 200, "successfully"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> updateEvent(@RequestBody EventDTO eventDTO) {
		try {
			Event event = eventService.updateEvent(eventDTO);
			if (CommonUtil.isNotNull(event)) {
				return ResponseEntity.ok().body(new IGenericResponse(eventService.save(event), 200, "successfully"));
			} else {
				return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found Event"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> deleteEvent(@RequestParam("event_id") Integer id) {
		try {
			Optional<Event> eventOptional = eventService.findById(id);
			if (eventOptional.isPresent()) {
				eventService.deleteById(id);
				return ResponseEntity.ok().body(new IGenericResponse(200, "successfully"));
			} else {
				return ResponseEntity.badRequest().body(new IGenericResponse(400, "Không tìm thấy Event"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findAll")
	public ResponseEntity<?> findAll(@RequestParam(value = "name", required = false) String name) {
		return ResponseEntity.ok().body(new IGenericResponse<>(eventService.findAllEvent(name), 200, "successfully"));
	}

	@DeleteMapping("deleteByListId")
	public ResponseEntity<?> deleteArrayTagId(@RequestBody ListEventId listEventId) {
		try {
			if (listEventId.getListId().isEmpty()) {
				return ResponseEntity.badRequest().body(new IGenericResponse(400, "nothing deleted"));
			}
			eventService.deleteListEventById(listEventId.getListId());
			return ResponseEntity.ok().body(new IGenericResponse(200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
