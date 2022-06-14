package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Order;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.DetailOrderService;
import com.example.adambackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("user/order")
public class OrderWebsiteController {
    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;
    @Autowired
    DetailOrderService detailOrderService;

//    @GetMapping("findTop5OrderByCreateTime")
//    public ResponseEntity<?> findTop5OrderByCreateTime(@RequestParam("account_id") Integer accountId) {
//        Optional<Account> account = accountService.findById(accountId);
//        if (account.isPresent()) {
//            return ResponseEntity.ok().body(new IGenericResponse<List<Order>>(orderService.findTop5ByOrderLessThanOrderByCreateDateDesc(accountId), 200, ""));
//        }
//        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Order"));
//    }
    @PostMapping("create")
    public ResponseEntity<?> createOrder(@RequestBody Order order){
        return  ResponseEntity.ok().body(new IGenericResponse<>(orderService.save(order),200,""));
    }
    @DeleteMapping("delete")
    public ResponseEntity<?>deleteOrder(@RequestParam("order_id")Integer orderId){
        Optional<Order> optionalOrder= orderService.findById(orderId);
        if(optionalOrder.isPresent()){
            detailOrderService.deleteAllByOrderId(orderId);
            orderService.deleteById(orderId);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200,""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Order"));

    }
    


}
