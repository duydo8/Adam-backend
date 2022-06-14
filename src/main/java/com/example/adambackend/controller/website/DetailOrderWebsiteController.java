package com.example.adambackend.controller.website;

import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.DetailOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("orderDetail")
public class DetailOrderWebsiteController {
    @Autowired
    DetailOrderService detailOrderService;

    @GetMapping("findAllByOrderId")
    public ResponseEntity<?> findAllByOrderId(@RequestParam("order_id") Integer orderId) {
        return ResponseEntity.ok().body(new IGenericResponse<List<DetailOrder>>(detailOrderService.findAllByOrderId(orderId), 200, ""));
    }

    @GetMapping("findTop10ProductByCountQuantityInOrderDetail")
    public ResponseEntity<?> findTop10ProductByCountQuantityInOrderDetail() {
        return ResponseEntity.ok().body(new IGenericResponse<List<Product>>(detailOrderService.findTop10ProductByCountQuantityInOrderDetail(), 200, ""));
    }
}
