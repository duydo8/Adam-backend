package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Account;
import com.example.adambackend.exception.ResourceNotFoundException;
import com.example.adambackend.repository.AccountRepository;
import com.example.adambackend.security.CurrentUser;
import com.example.adambackend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("account")
public class AccountWebsiteController {
    @Autowired
    private AccountRepository accountRepository;



    public Account getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return accountRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }


}
