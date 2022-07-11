package com.example.adambackend.controller.website;

import com.example.adambackend.config.TwilioSendSms;
import com.example.adambackend.entities.Account;
import com.example.adambackend.enums.ERoleName;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.request.SignUpRequest;
import com.example.adambackend.payload.response.AccountDto;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("account")
public class AccountWebsiteController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ModelMapper modelMapper;
    @PostMapping("/createAccount")
    public ResponseEntity<IGenericResponse> registerAccount(@RequestBody SignUpRequest signUpRequest) {
        if (accountService.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new IGenericResponse(400, "Username has been used"));
        }

        if (accountService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new IGenericResponse(400, "Email has been used"));
        }
        if(accountService.findByPhoneNumber(signUpRequest.getPhoneNumber()).isPresent()){
            return ResponseEntity
                    .badRequest()
                    .body(new IGenericResponse(400, "PhoneNumber has been used"));
        }


        Account account = new Account(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()),signUpRequest.getPhoneNumber(),
                signUpRequest.getFullName()
        );
        account.setIsActive(false);
        account.setIsDelete(false);
        account.setCreateDate(LocalDateTime.now());
        if (signUpRequest.getRole().equalsIgnoreCase(String.valueOf(ERoleName.Admin))) {
            account.setRole(ERoleName.Admin);
        } else {
            account.setRole(ERoleName.User);
        }

        int x=new Random().nextInt(899999)+100000;

        account.setVerificationCode(String.format("%06d",x));
        account.setTimeValid(LocalDateTime.now().plusMinutes(30));
        System.out.println("------------"+x);
        TwilioSendSms twilioSendSms= new TwilioSendSms();
        twilioSendSms.sendCode(account.getPhoneNumber(),x+"");
        Account account1 = accountService.save(account);

        AccountDto accountDto = modelMapper.map(account1, AccountDto.class);

        return ResponseEntity.ok().body(new IGenericResponse(accountDto, 200, "sign up succrssfully"));
    }
    @GetMapping("verify")
    public ResponseEntity<?> verify(@RequestParam("code")String code,@RequestParam("id")Integer id){
        Optional<Account> accountOptional= accountService.findById(id);
        if(accountOptional.isPresent()){
            if(accountOptional.get().getVerificationCode().equals(code)
                    && accountOptional.get().getTimeValid()==LocalDateTime.now()){
                accountOptional.get().setIsActive(true);
                ;
                return ResponseEntity.ok().body(new IGenericResponse<>(accountService.save(accountOptional.get()),200,""));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"code is not correct or time is invalid"));

        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));

    }
    @PostMapping("forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email,
                                            @RequestParam("password") String password,
                                            @RequestParam("confirm") String confirm) {
        Optional<Account> accountOptional = accountService.findByEmail(email);
        if (accountOptional.isPresent()) {
            if (password.equals(confirm)) {
                accountOptional.get().setPassword(passwordEncoder.encode(password));

                return ResponseEntity.ok().body(new IGenericResponse<>(accountService.save(accountOptional.get()), 200, ""));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "confirm is not equal password"));

            }

        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found account"));
        }

    }
    @GetMapping("findById")
    public ResponseEntity<?> findById(@RequestParam("id")Integer id){
        Optional<Account> accountOptional = accountService.findById(id);
        if (accountOptional.isPresent()) {
            return ResponseEntity.ok().body(new IGenericResponse<>(accountOptional.get(),200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found "));
        }
    }
}
