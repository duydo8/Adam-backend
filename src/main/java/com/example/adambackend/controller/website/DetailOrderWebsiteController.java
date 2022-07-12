package com.example.adambackend.controller.website;

import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.entities.Order;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.detailOrder.DetailOrderWebsiteCreate;
import com.example.adambackend.payload.detailOrder.DetailOrderWebsiteUpdate;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.DetailOrderService;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("orderDetail")
public class DetailOrderWebsiteController {
    @Autowired
    DetailOrderService detailOrderService;
    @Autowired
    DetailProductService detailProductService;
    @Autowired
    OrderService orderService;

    @GetMapping("findAllByOrderId")
    public ResponseEntity<?> findAllByOrderId(@RequestParam("order_id") Integer orderId) {
        return ResponseEntity.ok().body(new IGenericResponse<List<DetailOrder>>(detailOrderService.findAllByOrderId(orderId), 200, ""));
    }


    @PostMapping("create")
    public ResponseEntity<?> creatSize(@RequestBody DetailOrderWebsiteCreate detailOrderWebsiteCreate) {
        Optional<Order> order = orderService.findById(detailOrderWebsiteCreate.getOrderId());
        if (order.isPresent()) {
            DetailOrder detailOrder = new DetailOrder();
            detailOrder.setIsActive(true);
            detailOrder.setIsDeleted(false);
            detailOrder.setQuantity(detailOrderWebsiteCreate.getQuantity());
            detailOrder.setCreateDate(LocalDateTime.now());
            detailOrder.setDetailProduct(detailProductService.findById(detailOrderWebsiteCreate.getDetailProductId()).get());
            detailOrder.setOrder(order.get());
            detailOrder.setPrice(detailOrderWebsiteCreate.getPrice());
            return ResponseEntity.ok().body(new IGenericResponse<DetailOrder>(detailOrderService.save(detailOrder), 200, "success"));

        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));

    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody DetailOrderWebsiteUpdate detailOrderWebsiteUpdate) {
        Optional<DetailOrder> detailOrder1 = detailOrderService.findById(detailOrderWebsiteUpdate.getId());
        if (detailOrder1.isPresent()) {
            DetailOrder detailOrder = detailOrder1.get();
            detailOrder.setPrice(detailOrderWebsiteUpdate.getPrice());
            detailOrder.setQuantity(detailOrderWebsiteUpdate.getQuantity());
            return ResponseEntity.ok().body(new IGenericResponse<DetailOrder>(detailOrderService.save(detailOrder), 200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("detail_order_id") Integer id) {
        Optional<DetailOrder> detailOrder = detailOrderService.findById(id);
        if (detailOrder.isPresent()) {
            detailOrderService.deleteById(id);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }

    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(new IGenericResponse<List<DetailOrder>>(detailOrderService.findAll(), 200, ""));
    }

}
