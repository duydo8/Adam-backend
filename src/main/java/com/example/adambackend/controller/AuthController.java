package com.example.adambackend.controller;


import com.example.adambackend.entities.Account;
import com.example.adambackend.enums.ERoleName;
import com.example.adambackend.payload.request.AccountLoginRequestDto;
import com.example.adambackend.payload.response.AccountDto;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.payload.response.JwtResponse;
import com.example.adambackend.repository.AccountRepository;
import com.example.adambackend.security.AccountDetailsService;
import com.example.adambackend.security.jwtConfig.JwtUtils;
import com.example.adambackend.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


@RestController
@CrossOrigin("*")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountRepository accountRepository;


    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    AccountService accountService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AccountLoginRequestDto loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        AccountDetailsService userDetails = (AccountDetailsService) authentication.getPrincipal();
        ERoleName roles = accountRepository.findByUsername(loginRequest.getUsername()).get().getRole();


        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                String.valueOf(roles)));
    }


    @PostMapping("/process_register")
    public ResponseEntity<?> processRegister(@RequestBody Account account, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        System.out.println(account.toString());
        account.setRole(ERoleName.User);
        accountService.register(account, getSiteURL(request));
        AccountDto accountDto = modelMapper.map(account, AccountDto.class);

        return ResponseEntity.ok(new IGenericResponse<AccountDto>(accountDto, 200, "register successfully please check email before loginning"));
    }

    @RequestMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestParam("code") String code) {
        if (accountService.verify(code)) {
            return ResponseEntity.ok(new IGenericResponse<>(200, "verify_success"));
        } else {
            return ResponseEntity.ok(new IGenericResponse<>(400, "verify_fail"));
        }
    }

    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
