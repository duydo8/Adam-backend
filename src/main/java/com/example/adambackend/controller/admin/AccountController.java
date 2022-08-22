package com.example.adambackend.controller.admin;

import com.example.adambackend.config.TwilioSendSms;
import com.example.adambackend.entities.Account;
import com.example.adambackend.enums.ERoleName;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.account.AccountAdminCreate;
import com.example.adambackend.payload.account.AccountAdminDTO;
import com.example.adambackend.payload.account.AccountArrayId;
import com.example.adambackend.payload.account.AccountResponse;
import com.example.adambackend.payload.order.Dashboard;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/admin/account")
public class AccountController {
    private final List<String> thang = Arrays.asList("January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December");
    @Autowired
    AccountService accountService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/createAccount")
    public ResponseEntity<IGenericResponse> registerUser(@RequestBody AccountAdminCreate accountAdminCreate) {
        try {
            if (accountService.existsByUsername(accountAdminCreate.getUsername())) {
                return ResponseEntity
                        .badRequest()
                        .body(new IGenericResponse(400, "Username has been used"));
            }

            if (accountService.existsByEmail(accountAdminCreate.getEmail())) {
                return ResponseEntity
                        .badRequest()
                        .body(new IGenericResponse(400, "Email has been used"));
            }
            if (accountService.existsByPhoneNumber(accountAdminCreate.getPhoneNumber())) {
                return ResponseEntity
                        .badRequest()
                        .body(new IGenericResponse(400, "PhoneNumber has been used"));
            }

            Account account = new Account();
            account.setPriority(0.0);
            account.setUsername(accountAdminCreate.getUsername());
            account.setEmail(accountAdminCreate.getEmail());
            if (accountAdminCreate.getRole().equalsIgnoreCase("admin")) {
                account.setRole(ERoleName.Admin);
            } else if (accountAdminCreate.getRole().equalsIgnoreCase("user")) {
                account.setRole(ERoleName.User);
            } else {
                return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Can't find Role"));

            }
            account.setEmail(accountAdminCreate.getEmail());
            account.setPassword(passwordEncoder.encode(accountAdminCreate.getPassword()));
            account.setFullName(accountAdminCreate.getFullName());
            account.setCreateDate(LocalDateTime.now());
            account.setPhoneNumber(accountAdminCreate.getPhoneNumber());
            account.setIsActive(true);
            account.setIsDelete(false);
            return ResponseEntity.ok().body(new IGenericResponse<>(accountService.save(account), 200, "thành công"));
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


    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        try {
            List<AccountResponse> accountList = accountService.findAll();

            return ResponseEntity.ok(new IGenericResponse<List<AccountResponse>>(accountList, 200, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody AccountAdminDTO accountAdminDTO) {
        try {

            Optional<Account> accountOptional = accountService.findById(accountAdminDTO.getId());
            if (accountOptional.isPresent()) {
                accountOptional.get().setFullName(accountAdminDTO.getFullName());
                accountOptional.get().setEmail(accountAdminDTO.getEmail());

                accountOptional.get().setIsActive(accountAdminDTO.getIsActive());

                accountOptional.get().setPassword(passwordEncoder.encode(accountAdminDTO.getPassword()));

                accountService.save(accountOptional.get());
                return ResponseEntity.ok().body(new IGenericResponse<>(accountAdminDTO, 200, "success"));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not valid data"));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteEvent(@RequestParam("id") Integer id) {
        try {
            Optional<Account> accountOptional = accountService.findById(id);
            if (accountOptional.isPresent()) {
                accountService.deleteById(id);
                return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy "));
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
                return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }

    }

    //    @DeleteMapping("deleteListId")
    @GetMapping("accountSatistic")
    public ResponseEntity<?> countTotalAccountInOrder() {
        try {
            Dashboard dashboard = new Dashboard();
            dashboard.setName("Tổng số người dùng mua hàng");

            List<Double> doubleList = Arrays.asList(accountService.countTotalAccountInOrder(1),
                    accountService.countTotalAccountInOrder(2),
                    accountService.countTotalAccountInOrder(3),
                    accountService.countTotalAccountInOrder(4),
                    accountService.countTotalAccountInOrder(5),
                    accountService.countTotalAccountInOrder(6),
                    accountService.countTotalAccountInOrder(7),
                    accountService.countTotalAccountInOrder(8),
                    accountService.countTotalAccountInOrder(8),
                    accountService.countTotalAccountInOrder(9),
                    accountService.countTotalAccountInOrder(10),
                    accountService.countTotalAccountInOrder(11),
                    accountService.countTotalAccountInOrder(12));
            dashboard.setData(doubleList);
            dashboard.setLabels(thang);
            Dashboard dashboard1 = new Dashboard();
            dashboard1.setName("Tổng số người dùng ");

            List<Double> doubleList1 = Arrays.asList(accountService.countTotalAccount(1),
                    accountService.countTotalAccount(2),
                    accountService.countTotalAccount(3),
                    accountService.countTotalAccount(4),
                    accountService.countTotalAccount(5),
                    accountService.countTotalAccount(6),
                    accountService.countTotalAccount(7),
                    accountService.countTotalAccount(8),
                    accountService.countTotalAccount(9),
                    accountService.countTotalAccount(10),
                    accountService.countTotalAccount(11),
                    accountService.countTotalAccount(12)
            );

            dashboard1.setData(doubleList1);
            dashboard1.setLabels(thang);
            Dashboard dashboard2 = new Dashboard();
            dashboard2.setName("Tổng số người đăng ký mới");

            List<Double> doubleList2 = Arrays.asList(accountService.countTotalSignUpAccount(1),
                    accountService.countTotalSignUpAccount(2),
                    accountService.countTotalSignUpAccount(3),
                    accountService.countTotalSignUpAccount(4),
                    accountService.countTotalSignUpAccount(5),
                    accountService.countTotalSignUpAccount(6),
                    accountService.countTotalSignUpAccount(7),
                    accountService.countTotalSignUpAccount(8),
                    accountService.countTotalSignUpAccount(9),
                    accountService.countTotalSignUpAccount(10),
                    accountService.countTotalSignUpAccount(11),
                    accountService.countTotalSignUpAccount(12)
            );

            dashboard2.setData(doubleList2);
            dashboard2.setLabels(thang);
            List<Dashboard> dashboardList = Arrays.asList(dashboard, dashboard1, dashboard2);
            return ResponseEntity.ok().body(new IGenericResponse<>(dashboardList, 200, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PutMapping("deleteByArrayId")
    public ResponseEntity<?> deleteByArrayId(@RequestBody AccountArrayId accountArrayId) {
        try {
            List<Integer> accountIdList = accountArrayId.getAccountIdList();
            for (Integer x : accountIdList
            ) {
                if (accountService.findById(x).isPresent()) {
                    accountService.updateAccountDeleted(x);
                }
            }
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PostMapping("updatePriority")
    public ResponseEntity<?> updatePriority(@RequestParam("id") Integer id,
                                            @RequestParam("priority") Double priority) {
        Optional<Account> account = accountService.findById(id);
        if (account.isPresent()) {
            account.get().setPriority(priority);
            accountService.save(account.get());
            return ResponseEntity.ok().body(new IGenericResponse<>("", 200, "Thành công"));
        }
        return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "ko tìm thấy"));
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
