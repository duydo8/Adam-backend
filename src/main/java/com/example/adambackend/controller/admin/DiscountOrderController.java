package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.*;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.discountOrder.DiscountOrderCreate;
import com.example.adambackend.payload.discountOrder.DiscountOrderUpdate;
import com.example.adambackend.payload.discountProduct.DiscountProductCreate;
import com.example.adambackend.payload.discountProduct.DiscountProductUpdate;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.DiscountOrderRepository;
import com.example.adambackend.repository.DiscountProductRepository;
import com.example.adambackend.repository.EventRepository;
import com.example.adambackend.repository.OrderRepository;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 36000000)
@RequestMapping("admin/discountOrder")
public class DiscountOrderController {
    @Autowired
    DiscountOrderRepository discountOrderRepository;
    @Autowired
    DiscountProductRepository discountProductRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    ProductSevice productSevice;
    @Autowired
    DetailProductService detailProductService;
    @Autowired
    OrderRepository orderRepository;

    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(new IGenericResponse<>(discountProductRepository.findAll(), 200, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
    @GetMapping("findById")
    public ResponseEntity<?> findById(@RequestParam("id") Integer id) {
        try {
            Optional<DiscountOrder> discountOrderOptional = discountOrderRepository.findById(id);
            if (discountOrderOptional.isPresent()) {
                return ResponseEntity.ok(new IGenericResponse<>(discountOrderOptional.get(), 200, ""));

            }
            return ResponseEntity.ok().body(new HandleExceptionDemo(400, "not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("findByEventId")
    public ResponseEntity<?> findByEventId(@RequestParam("event_id") Integer eventId) {
        try {
            Optional<Event> eventOptional = eventRepository.findById(eventId);
            if (eventOptional.isPresent()) {
                List<DiscountOrder> discountOrders=discountOrderRepository.findByEventId(eventId);
                return ResponseEntity.ok(new IGenericResponse<>(eventOptional.get(), 200, ""));

            }
            return ResponseEntity.ok(new HandleExceptionDemo(400, "not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("create")
    public ResponseEntity<?> create(@RequestBody DiscountOrderCreate discountOrderCreate) {
        try {
            DiscountOrder discountOrder = new DiscountOrder();

            Optional<Event> eventOptional = eventRepository.findById(discountOrderCreate.getEventId());
            if ( eventOptional.isPresent()) {
                discountOrder.setOrderMinRange(discountOrderCreate.getOrderMinRange());
                discountOrder.setOrderMaxRange(discountOrderCreate.getOrderMaxRange());
                discountOrder.setEvent(eventOptional.get());
                discountOrder.setDiscountName(discountOrderCreate.getDiscountName());
                discountOrder.setCreateDate(LocalDateTime.now());
                discountOrder.setDescription(discountOrderCreate.getDescription());
                discountOrder.setIsActive(true);
                discountOrder.setIsDeleted(false);
                discountOrder.setStartTime(discountOrderCreate.getStartTime());
                discountOrder.setSalePrice(discountOrderCreate.getSalePrice());
                discountOrder.setEndTime(discountOrderCreate.getEndTime());


                return ResponseEntity.ok().body(new IGenericResponse<>(discountOrderRepository.save(discountOrder), 200, ""));
            }
            return ResponseEntity.ok(new HandleExceptionDemo(400, "not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody DiscountOrderUpdate discountOrderUpdate) {
        try {
            Optional<DiscountOrder> discountOrderOptional = discountOrderRepository.findById(discountOrderUpdate.getId());
            if ( discountOrderOptional.isPresent()) {
                discountOrderOptional.get().setDiscountName(discountOrderUpdate.getDiscountName());
                discountOrderOptional.get().setIsActive(discountOrderUpdate.getIsActive());

                discountOrderOptional.get().setSalePrice(discountOrderUpdate.getSalePrice());
                discountOrderOptional.get().setOrderMaxRange(discountOrderUpdate.getOrderMaxRange());
                discountOrderOptional.get().setOrderMinRange(discountOrderUpdate.getOrderMinRange());

                return ResponseEntity.ok().body(new IGenericResponse<>(discountOrderRepository.save(discountOrderOptional.get()), 200, ""));
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
            discountProductRepository.updateIsActive(id);
            return ResponseEntity.ok().body(new IGenericResponse<>("", 200, "Thành công"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
}
