package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Account;
import com.example.adambackend.enums.ERoleName;
import com.example.adambackend.payload.request.SignUpRequest;
import com.example.adambackend.payload.response.AccountDto;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/admin/account")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;
    @PostMapping("/process_register")
    public ResponseEntity<?> processRegister(@RequestBody Account account, HttpServletRequest request)
            throws UnsupportedEncodingException, MessagingException {
        System.out.println(account.toString());
        accountService.register(account, getSiteURL(request));

        return ResponseEntity.ok(new IGenericResponse<Account>(account,200,"register successfully please check email before loginning"));
    }
    @RequestMapping("/verify")
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
    @PostMapping("/createAccount")
    public ResponseEntity<IGenericResponse> registerUser(@RequestBody SignUpRequest signUpRequest) {
        if (accountService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new IGenericResponse(400,"Username has been used"));
        }

        if (accountService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new IGenericResponse(400,"Email has been used"));
        }


        Account account = new Account(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword())
        );
        if(signUpRequest.getRole().equalsIgnoreCase(String.valueOf(ERoleName.Admin))){
            account.setRole(ERoleName.Admin);
        }else{
            account.setRole(ERoleName.User);
        }


        Account account1= accountService.create(account);
        AccountDto accountDto= modelMapper.map(account1,AccountDto.class);

        return ResponseEntity.ok().body(new IGenericResponse(accountDto,200,"sign up succrssfully"));
    }
}
