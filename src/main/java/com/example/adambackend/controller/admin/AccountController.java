package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Account;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/admin/account")
public class AccountController {
    @Autowired
    AccountService accountService;
    @PostMapping("/process_register")
    public ResponseEntity<?> processRegister(@RequestBody Account account, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        System.out.println(account.toString());
        accountService.register(account, getSiteURL(request));

        return ResponseEntity.ok(new IGenericResponse<Account>(account,200,"register successfully please check email before loginning"));
    }
    @GetMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("code") String code) {
        if (accountService.verify(code)) {
            return ResponseEntity.ok(new IGenericResponse<>(200,"verify_success"));
        } else {
            return ResponseEntity.ok(new IGenericResponse<>(400,"verify_fail"));
        }
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
