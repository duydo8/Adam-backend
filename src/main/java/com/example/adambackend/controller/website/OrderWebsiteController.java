package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Address;
import com.example.adambackend.entities.CartItems;
import com.example.adambackend.entities.Order;
import com.example.adambackend.enums.OrderStatus;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.order.OrderWebsiteCreate;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.AddressService;
import com.example.adambackend.service.DetailOrderService;
import com.example.adambackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("user/order")
public class OrderWebsiteController {
    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;
    @Autowired
    DetailOrderService detailOrderService;
    @Autowired
    AddressService addressService;

    //    @GetMapping("findTop5OrderByCreateTime")
//    public ResponseEntity<?> findTop5OrderByCreateTime(@RequestParam("account_id") Integer accountId) {
//        Optional<Account> account = accountService.findById(accountId);
//        if (account.isPresent()) {
//            return ResponseEntity.ok().body(new IGenericResponse<List<Order>>(orderService.findTop5ByOrderLessThanOrderByCreateDateDesc(accountId), 200, ""));
//        }
//        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Order"));
//    }
    @PostMapping("create")
    public ResponseEntity<?> createOrder(@RequestBody OrderWebsiteCreate orderWebsiteCreate) {
        Optional<Account> account= accountService.findById(orderWebsiteCreate.getAccountId());
        Optional<Address> address= addressService.findById(orderWebsiteCreate.getAddressId());
        if(address.isPresent() && account.isPresent()){
        Order order= new Order();
        order.setAccount(account.get());
        order.setCreateDate(LocalDateTime.now());
        order.setStatus(OrderStatus.pending);
        order.setAddress(address.get());
        order.setFullName(orderWebsiteCreate.getFullName());
        order.setPhoneNumber(orderWebsiteCreate.getPhoneNumber());
        order.setTotalPrice(orderWebsiteCreate.getTotalPrice());
        order.setSalePrice(orderWebsiteCreate.getSalePrice());
        order.setAmountPrice(orderWebsiteCreate.getAmountPrice());
        order.setAddressDetail(orderWebsiteCreate.getAddressDetail());
        return ResponseEntity.ok().body(new IGenericResponse<>(orderService.save(order), 200, ""));
    }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found "));

    }


    @DeleteMapping("delete")
    public ResponseEntity<?> deleteOrder(@RequestParam("order_id") Integer orderId) {
        Optional<Order> optionalOrder = orderService.findById(orderId);
        if (optionalOrder.isPresent()) {
            detailOrderService.deleteAllByOrderId(orderId);
            orderService.deleteById(orderId);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Order"));

    }
    @GetMapping("findById")
    public ResponseEntity<?> findById(@RequestParam("id")Integer id){
        Optional<Order> order= orderService.findById(id);
        if(order.isPresent()){
            return ResponseEntity.ok(new IGenericResponse<>(order.get(), 200, ""));

        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));

    }
    @GetMapping("findByAccountId")
    public ResponseEntity<?> findByAccountId(@RequestParam("account_id") Integer accountId){
        Optional<Account> account= accountService.findById(accountId);
        if(account.isPresent()){
            return ResponseEntity.ok().body(new IGenericResponse<>(orderService.findByAccountId(accountId),200,""));


        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));

    }

}
