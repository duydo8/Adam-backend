package com.example.adambackend.controller.admin;

import com.example.adambackend.repository.DiscountOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/discountOrder")
public class DiscountOrderController {
    @Autowired
    DiscountOrderRepository discountOrderRepository;
}
