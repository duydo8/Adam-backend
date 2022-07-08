package com.example.adambackend.controller.admin;

import com.example.adambackend.config.TwilioSendSms;
import com.example.adambackend.entities.Account;
import com.example.adambackend.enums.ERoleName;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.account.AccountAdminDTO;
import com.example.adambackend.payload.account.AccountArrayId;
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
        account.setCreateDate(LocalDateTime.now());
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
        account.setPriority(5.0);
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
        account.setCreateDate(LocalDateTime.now());
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
        account.setPriority(5.0);
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
        try {
            Optional<Account> accountOptional = accountService.findById(accountAdminDTO.getId());
            if (accountOptional.isPresent()) {
                Account account = modelMapper.map(accountAdminDTO, Account.class);
                if (accountAdminDTO.getRole().equalsIgnoreCase("admin")) {
                    account.setRole(ERoleName.Admin);
                } else account.setRole(ERoleName.User);

                accountService.save(account);

                return ResponseEntity.ok().body(new IGenericResponse<>(accountAdminDTO, 200, "success"));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
        }catch (Exception e){
          return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not valid data"));
        }
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
@GetMapping("accountSatistic")
    public ResponseEntity<?> countTotalAccountInOrder(){
    Dashboard dashboard= new Dashboard();
    dashboard.setName("Tổng số người dùng mua hàng");

    List<Double> doubleList= Arrays.asList(accountService.countTotalAccountInOrder(1),
   accountService.countTotalAccountInOrder(2),
    accountService.countTotalAccountInOrder(3),
   accountService.countTotalAccountInOrder(4),
   accountService.countTotalAccountInOrder(5),
    accountService.countTotalAccountInOrder(6));

    dashboard.setData(doubleList);
    dashboard.setLabels(thang);
    Dashboard dashboard1= new Dashboard();
    dashboard1.setName("Tổng số người dùng ");

    List<Double> doubleList1= Arrays.asList(accountService.countTotalAccount(1),
            accountService.countTotalAccount(2),
            accountService.countTotalAccount(3),
            accountService.countTotalAccount(4),
            accountService.countTotalAccount(5),
            accountService.countTotalAccount(6));

    dashboard1.setData(doubleList1);
    dashboard1.setLabels(thang);
    Dashboard dashboard2= new Dashboard();
    dashboard2.setName("Tổng số người đăng ký mới");

    List<Double> doubleList2= Arrays.asList(accountService.countTotalSignUpAccount(1),
            accountService.countTotalSignUpAccount(2),
            accountService.countTotalSignUpAccount(3),
            accountService.countTotalSignUpAccount(4),
            accountService.countTotalSignUpAccount(5),
            accountService.countTotalSignUpAccount(6));

    dashboard2.setData(doubleList2);
    dashboard2.setLabels(thang);
List<Dashboard> dashboardList= Arrays.asList(dashboard,dashboard1,dashboard2);
    return ResponseEntity.ok().body(new IGenericResponse<>(dashboardList,200,""));
}
@PutMapping("deleteByArrayId")
    public ResponseEntity<?> deleteByArrayId(@RequestBody AccountArrayId accountArrayId){
        List<Integer> accountIdList=accountArrayId.getAccountIdList();
    for (Integer x: accountIdList
         ) {
        if(accountService.findById(x).isPresent()) {
            accountService.updateAccountDeleted(x);
        }
    }
    return ResponseEntity.ok().body(new HandleExceptionDemo(200,"success"));
}

}
