package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Order;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("admin/order")
public class OrderController {
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> page1 = orderRepository.findAll(pageable);
        return ResponseEntity.ok().body(new IGenericResponse<Page<Order>>(page1, 200, "Page product"));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateEvent(@RequestBody Order order) {
        Optional<Order> materialOptional = orderRepository.findById(order.getId());
        if (materialOptional.isPresent()) {

            return ResponseEntity.ok().body(new IGenericResponse<Order>(orderRepository.save(order), 200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Ward"));
        }
    }
}
