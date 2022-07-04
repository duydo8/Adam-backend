package com.example.adambackend.controller.admin;

import com.example.adambackend.config.TwilioSendSms;
import com.example.adambackend.entities.Account;
import com.example.adambackend.enums.ERoleName;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.account.AccountAdminDTO;
import com.example.adambackend.payload.account.AccountResponse;
import com.example.adambackend.payload.order.Dashboard;
import com.example.adambackend.payload.request.SignUpRequest;
import com.example.adambackend.payload.response.AccountDto;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import net.bytebuddy.utility.RandomString;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/admin/account")
public class AccountController {
    @Autowired
    AccountService accountService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;

    private final List<String> thang= Arrays.asList("January", "February", "March", "April", "May",
            "June", "July");

    @PostMapping("/createAccount")
    public ResponseEntity<IGenericResponse> registerUser(@RequestBody SignUpRequest signUpRequest) {
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
        if (signUpRequest.getRole().equalsIgnoreCase(String.valueOf(ERoleName.Admin))) {
            account.setRole(ERoleName.Admin);
        } else {
            account.setRole(ERoleName.User);
        }
        String code= RandomString.make(64);
        account.setVerificationCode(code);
        account.setTimeValid(LocalDateTime.now().plusMinutes(30));
        TwilioSendSms twilioSendSms= new TwilioSendSms();
        twilioSendSms.sendCode(account.getPhoneNumber(),code);

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


    @PostMapping("/createAdminAccount")
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
        account.setIsActive(true);
        account.setIsDelete(false);
        if (signUpRequest.getRole().equalsIgnoreCase(String.valueOf(ERoleName.Admin))) {
            account.setRole(ERoleName.Admin);
        } else {
            account.setRole(ERoleName.User);
        }
        String code= RandomString.make(64);
        account.setVerificationCode(code);
        account.setTimeValid(LocalDateTime.now().plusMinutes(30));
//        TwilioSendSms twilioSendSms= new TwilioSendSms();
//        twilioSendSms.sendCode(account.getPhoneNumber(),code);

        Account account1 = accountService.save(account);

        AccountDto accountDto = modelMapper.map(account1, AccountDto.class);

        return ResponseEntity.ok().body(new IGenericResponse(accountDto, 200, "sign up succrssfully"));
    }
    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        List<AccountResponse> accountList = accountService.findAll();

        return ResponseEntity.ok(new IGenericResponse<List<AccountResponse>>(accountList, 200, ""));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody AccountAdminDTO accountAdminDTO) {
        Optional<Account> accountOptional = accountService.findById(accountAdminDTO.getId());
        if (accountOptional.isPresent()) {
            Account account= modelMapper.map(accountAdminDTO,Account.class);
            if(accountAdminDTO.getRole().equalsIgnoreCase("admin")){
                account.setRole(ERoleName.Admin);
            }else account.setRole(ERoleName.User);

            accountService.save(account);

            return ResponseEntity.ok().body(new IGenericResponse<>(accountAdminDTO, 200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteEvent(@RequestParam("id") Integer id) {
        Optional<Account> accountOptional = accountService.findById(id);
        if (accountOptional.isPresent()) {
            accountService.deleteById(id);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found "));
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
//    @DeleteMapping("deleteListId")
@GetMapping("countTotalAccountInOrder")
    public ResponseEntity<?> countTotalAccountInOrder(){
    Dashboard dashboard= new Dashboard();
    dashboard.setName("Tổng số người dùng mua hàng");
    Double thang1=accountService.countTotalAccountInOrder(1);
    Double thang2=accountService.countTotalAccountInOrder(2);
    Double thang3=accountService.countTotalAccountInOrder(3);
    Double thang4=accountService.countTotalAccountInOrder(4);
    Double thang5=accountService.countTotalAccountInOrder(5);
    Double thang6=accountService.countTotalAccountInOrder(6);
    List<Double> doubleList= new ArrayList<>();
    doubleList.add(thang1);
    doubleList.add(thang2);
    doubleList.add(thang3);
    doubleList.add(thang4);
    doubleList.add(thang5);
    doubleList.add(thang6);
    dashboard.setData(doubleList);
    dashboard.setLabels(thang);
    return ResponseEntity.ok().body(new IGenericResponse<>(dashboard,200,""));
}
    @GetMapping("countTotalAccount")
    public ResponseEntity<?> countTotalAccount(){
        Dashboard dashboard= new Dashboard();
        dashboard.setName("Tổng người dùng");
        Double thang1=accountService.countTotalAccount(1);
        Double thang2=accountService.countTotalAccount(2);
        Double thang3=accountService.countTotalAccount(3);
        Double thang4=accountService.countTotalAccount(4);
        Double thang5=accountService.countTotalAccount(5);
        Double thang6=accountService.countTotalAccount(6);
        List<Double> doubleList= new ArrayList<>();
        doubleList.add(thang1);
        doubleList.add(thang2);
        doubleList.add(thang3);
        doubleList.add(thang4);
        doubleList.add(thang5);
        doubleList.add(thang6);
        dashboard.setData(doubleList);
        dashboard.setLabels(thang);
        return ResponseEntity.ok().body(new IGenericResponse<>(dashboard,200,""));
    }
    @GetMapping("countTotalSignUpAccount")
    public ResponseEntity<?> countTotalSignUpAccount(){
        Dashboard dashboard= new Dashboard();
        dashboard.setName("Tổng số người đăng ký mỡi");
        Double thang1=accountService.countTotalSignUpAccount(1);
        Double thang2=accountService.countTotalSignUpAccount(2);
        Double thang3=accountService.countTotalSignUpAccount(3);
        Double thang4=accountService.countTotalSignUpAccount(4);
        Double thang5=accountService.countTotalSignUpAccount(5);
        Double thang6=accountService.countTotalSignUpAccount(6);
        List<Double> doubleList= new ArrayList<>();
        doubleList.add(thang1);
        doubleList.add(thang2);
        doubleList.add(thang3);
        doubleList.add(thang4);
        doubleList.add(thang5);
        doubleList.add(thang6);
        dashboard.setData(doubleList);
        dashboard.setLabels(thang);
        return ResponseEntity.ok().body(new IGenericResponse<>(dashboard,200,""));
    }


}
