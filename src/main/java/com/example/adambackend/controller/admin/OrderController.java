package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.*;
import com.example.adambackend.enums.OrderStatus;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.OrderDTO;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.OrderRepository;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    DetailProductService detailProductService;
    @Autowired
    AccountService accountService;

    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> page1 = orderService.findAll(pageable);
        return ResponseEntity.ok().body(new IGenericResponse<Page<Order>>(page1, 200, "Page product"));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateEvent(@RequestBody Order order) {
        Optional<Order> materialOptional = orderService.findById(order.getId());
        if (materialOptional.isPresent()) {

            return ResponseEntity.ok().body(new IGenericResponse<Order>(orderService.save(order), 200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found event"));
        }
    }
    @PutMapping("updateSuccess")
    public ResponseEntity<?> updateEventSuccess(@RequestParam("orderId") Integer orderId){
        Optional<Order> orderOptional= orderService.findById(orderId);
        if(orderOptional.isPresent()){
            List<DetailOrder> detailOrderList= orderOptional.get().getDetailOrders();
            orderOptional.get().setStatus(OrderStatus.success);
            for (DetailOrder detailOrder: detailOrderList
                 ) {
                Integer detailProductId=detailOrder.getDetailProduct().getId();
                Integer quantity=detailOrder.getQuantity();
                Optional<DetailProduct> detailProductOptional=detailProductService.findById(detailProductId);
                detailProductOptional.get().setQuantity(detailProductOptional.get().getQuantity()-quantity);
                detailProductService.save(detailProductOptional.get());

            }
            Account account = accountService.findById(orderOptional.get().getAccount().getId()).get();
            if(account.getPriority()==10){

            }else{
                account.setPriority(account.getPriority());
            }

            return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));

        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found "));
    }


}
