package com.example.adambackend.controller.admin;

import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.DiscountOrderRepository;
import com.example.adambackend.repository.DiscountProductRepository;
import com.example.adambackend.repository.EventRepository;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(new IGenericResponse<>(discountProductRepository.findAll(), 200, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
}
