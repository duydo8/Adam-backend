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
    public ResponseEntity<IGenericResponse> registerUser(@RequestBody SignUpRequest signUpRequest) {
        try {
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
            System.out.println(signUpRequest.getPhoneNumber());
            Optional<Account> account = accountService.findByPhoneNumber(signUpRequest.getPhoneNumber());

            if (account.isPresent()) {
                account.get().setUsername(signUpRequest.getUsername());
                account.get().setEmail(signUpRequest.getEmail());
                account.get().setFullName(signUpRequest.getFullName());
                account.get().setCreateDate(LocalDateTime.now());
                account.get().setIsActive(true);
                account.get().setIsDelete(false);
                account.get().setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
                if (signUpRequest.getRole().equalsIgnoreCase(String.valueOf(ERoleName.Admin))) {
                    account.get().setRole(ERoleName.Admin);
                } else {
                    account.get().setRole(ERoleName.User);
                }

                account.get().setPriority(0.0);
                System.out.println(account.get().getTimeValid());
                if (LocalDateTime.now().isBefore(account.get().getTimeValid())) {
                    System.out.println(account.get().getVerificationCode());
                    System.out.println(signUpRequest.getCode());
                    if (account.get().getVerificationCode() == (signUpRequest.getCode())) {
                        account.get().setTimeValid(null);
                        account.get().setVerificationCode(null);
                        Account account1 = accountService.save(account.get());

                        AccountDto accountDto = modelMapper.map(account1, AccountDto.class);
                        return ResponseEntity.ok().body(new IGenericResponse(accountDto, 200, "thanh cong"));
                    }
                    return ResponseEntity.badRequest().body(new IGenericResponse("", 400, "Vui lòng nhập lại"));

                }
                accountService.deleteById(account.get().getId());
                return ResponseEntity.badRequest().body(new IGenericResponse(" ", 400, "Đã quá thời gian chờ"));

            }
            return ResponseEntity.badRequest().body(new IGenericResponse("", 400, "Vui lòng xác nhận lại số điện thoại của bạn"));


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PostMapping("verify")
    public ResponseEntity<?> verify(@RequestParam("phone_number") String phoneNumber) {
        try {
            Optional<Account> accountOptional = accountService.findByPhoneNumber(phoneNumber);

            if (accountOptional.isPresent() && accountOptional.get().getTimeValid() == null) {

                return ResponseEntity.badRequest().body(new IGenericResponse("", 400, "số điện thoại này đã được đăng ký"));
            }
            TwilioSendSms twilioSendSms = new TwilioSendSms();
            int code = new Random().nextInt(999999);
            phoneNumber = phoneNumber.substring(1, phoneNumber.length());
            twilioSendSms.sendCode(phoneNumber, code);
            Account account = new Account();
            account.setPhoneNumber(phoneNumber);
            account.setUsername(phoneNumber);
            account.setRole(ERoleName.User);
            account.setPassword(passwordEncoder.encode("123456"));
            account.setVerificationCode(code);
            account.setTimeValid(LocalDateTime.now().plusMinutes(30));
            accountService.save(account);
            return ResponseEntity.ok().body(new IGenericResponse(code, 200, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
    @GetMapping("sendCode")
    public ResponseEntity<?> sendCode(@RequestParam("phone_number")String phoneNumber){
        try {
            Optional<Account> accountOptional = accountService.findByPhoneNumber(phoneNumber);
            if(accountOptional.isPresent()){
                TwilioSendSms twilioSendSms = new TwilioSendSms();
                int code = new Random().nextInt(999999);
                twilioSendSms.sendCode(phoneNumber, code);
                accountOptional.get().setVerificationCode(code);
                accountOptional.get().setTimeValid(LocalDateTime.now().plusMinutes(30));
                accountService.save(accountOptional.get());
                return ResponseEntity.ok().body(new IGenericResponse<>(code,200,"thanh cong"));
            }
            return ResponseEntity.ok().body(new IGenericResponse<>(200,"không tìm thấy tài khoản"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
    @GetMapping("forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("phone_number") String phoneNumber,
                                            @RequestParam("password") String password,
                                            @RequestParam("confirm") String confirm,
                                            @RequestParam("code")Integer code) {
        try {
            Optional<Account> accountOptional = accountService.findByPhoneNumber(phoneNumber);
            if (accountOptional.isPresent()) {
                if(code==accountOptional.get().getVerificationCode()&& LocalDateTime.now().isAfter(accountOptional.get().getTimeValid())){
                    if (password.equals(confirm)) {
                        accountOptional.get().setPassword(passwordEncoder.encode(password));

                        return ResponseEntity.ok().body(new IGenericResponse<>(accountService.save(accountOptional.get()), 200, ""));
                    } else {
                        return ResponseEntity.ok().body(new HandleExceptionDemo(400, "confirm is not equal password"));

                    }
                }else{
                    return ResponseEntity.ok().body(new HandleExceptionDemo(400, " code không đúng hoặc quá hạn "));
                }


            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy account"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("findById")
    public ResponseEntity<?> findById(@RequestParam("id") Integer id) {
        try {
            Optional<Account> accountOptional = accountService.findById(id);
            if (accountOptional.isPresent()) {
                return ResponseEntity.ok().body(new IGenericResponse<>(accountOptional.get(), 200, ""));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy "));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }

    }

    @PostMapping("changePassword")
    public ResponseEntity<?> updatePriority(@RequestParam("id") Integer id,
                                            @RequestParam("password") String password,
                                            @RequestParam("passwordNew") String passNew,
                                            @RequestParam("confirm") String confirm) {
        Optional<Account> account = accountService.findById(id);
        if (account.isPresent()) {
            if (!account.get().getPassword().equals(password)) {
                return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "sai mật khẩu"));

            }
            if (!confirm.equals(passNew)) {
                return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Nhập lại mật khẩu không đúng"));

            }
            if (passNew.equals(password)) {
                return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Mật khẩu mới giống mật khẩu cũ"));

            }
            String x = passwordEncoder.encode(passNew);
            account.get().setPassword(x);
            accountService.save(account.get());
            return ResponseEntity.ok().body(new IGenericResponse<>("", 200, "Thành công"));
        }
        return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "ko tìm thấy"));
    }
}
