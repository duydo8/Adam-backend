package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Order;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("historyOrder")
public class OrderWebsiteController {
    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;
    @GetMapping("findTop5OrderByCreateTime")
    public ResponseEntity<?>findTop5OrderByCreateTime(@RequestParam("account_id")Long accountId){
        Optional<Account> account= accountService.findById(accountId);
        if(account.isPresent()){
            return ResponseEntity.ok().body(new IGenericResponse<List<Order>>(orderService.findTop5OrderByCreateTime(accountId),200,""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found Product"));
    }

}
