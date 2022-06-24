package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Account;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("account")
public class AccountWebsiteController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AccountService accountService;

    @PostMapping("forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email,
                                            @RequestParam("password") String password,
                                            @RequestParam("confirm") String confirm) {
        Optional<Account> accountOptional = accountService.findByEmail(email);
        if (accountOptional.isPresent()) {
            if (password.equals(confirm)) {
                accountOptional.get().setPassword(passwordEncoder.encode(password));
                ;
                return ResponseEntity.ok().body(new IGenericResponse<>(accountService.save(accountOptional.get()), 200, ""));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "confirm is not equal password"));

            }

        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found account"));
        }

    }


}
