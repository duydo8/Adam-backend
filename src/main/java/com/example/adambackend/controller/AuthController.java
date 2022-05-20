package com.example.adambackend.controller;


import com.example.adambackend.entities.Account;


import com.example.adambackend.enums.ERoleName;
import com.example.adambackend.payload.request.AccountLoginRequestDto;
import com.example.adambackend.payload.request.SignUpRequest;
import com.example.adambackend.payload.response.JwtResponse;
import com.example.adambackend.payload.response.MessageResponse;
import com.example.adambackend.repository.AccountRepository;
import com.example.adambackend.security.jwtConfig.JwtUtils;
import com.example.adambackend.service.impl.AccountDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController

public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountRepository accountRepository;



    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody AccountLoginRequestDto loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        AccountDetailsService userDetails = (AccountDetailsService) authentication.getPrincipal();
        ERoleName roles = accountRepository.findByUsername(loginRequest.getUsername()).get().getRoleName();


        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                String.valueOf(roles)));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (accountRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }


        Account account = new Account(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
                 );
        if(signUpRequest.getRoleName().equalsIgnoreCase(String.valueOf(ERoleName.Admin))){
            account.setRoleName(ERoleName.Admin);
        }else{
            account.setRoleName(ERoleName.User);
        }


       Account account1= accountRepository.save(account);

        return ResponseEntity.ok().body(account1);
    }
}
