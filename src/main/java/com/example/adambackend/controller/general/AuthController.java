package com.example.adambackend.controller.general;


import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.config.TwilioSendSms;
import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.UserInfo;
import com.example.adambackend.enums.ERoleName;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.request.AccountLoginRequestDto;
import com.example.adambackend.payload.request.SignUpRequest;
import com.example.adambackend.payload.response.AccountDto;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.payload.response.JwtResponse;
import com.example.adambackend.repository.AccountRepository;
import com.example.adambackend.repository.UserInfoRepository;
import com.example.adambackend.security.AccountDetailsService;
import com.example.adambackend.security.jwtConfig.JwtUtils;
import com.example.adambackend.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("auth")
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
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserInfoRepository userInfoRepository;
    @Value("${jwt.expirationDateInMS}")
    private int expirationDateInMS;

    @PostMapping("authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AccountLoginRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            AccountDetailsService userDetails = (AccountDetailsService) authentication.getPrincipal();
            ERoleName roles = accountRepository.findByUsername(loginRequest.getUsername()).get().getRole();

            List<UserInfo> userInfoList = userInfoRepository.findAll();
            if (userInfoList.size() > 0) {
                boolean check = false;
                for (UserInfo u : userInfoList
                ) {
                    if (u.getUsername().equals(loginRequest.getUsername())) {
                        u.setToken(jwt);
                        u.setTimeValid(LocalDateTime.now().plusSeconds(expirationDateInMS / 1000));
                        u.setStatus(1);
                        userInfoRepository.save(u);
                        check = true;
                        break;
                    }
                }
                if (check = false) {
                    UserInfo userInfo = new UserInfo();
                    userInfo.setUsername(loginRequest.getUsername());
                    userInfo.setToken(jwt);
                    userInfo.setTimeValid(LocalDateTime.now().plusSeconds(expirationDateInMS / 1000));
                    userInfo.setStatus(1);
                    userInfoRepository.save(userInfo);

                }

            }
            UserInfo userInfo = new UserInfo();
            userInfo.setUsername(loginRequest.getUsername());
            userInfo.setToken(jwt);
            userInfo.setTimeValid(LocalDateTime.now().plusSeconds(expirationDateInMS / 1000));
            userInfo.setStatus(1);
            userInfoRepository.save(userInfo);
            return ResponseEntity.ok(new IGenericResponse<>(new JwtResponse(jwt,
                    userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getEmail(),
                    String.valueOf(roles)), 200, "successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }


    @PostMapping("/createAccount")
    public ResponseEntity<IGenericResponse> registerUser(@RequestBody SignUpRequest signUpRequest) {
        try {
            Account account = accountService.findByUserNameAndEmailAndPhoneNumber(signUpRequest.getUsername(),
                    signUpRequest.getEmail(), signUpRequest.getPhoneNumber());

            if (CommonUtil.isNotNull(account)) {
                if (account.getUsername().equals(signUpRequest.getUsername())) {
                    return ResponseEntity.ok().body(new IGenericResponse(200, "Username has been used"));
                }
                if (account.getEmail().equals(signUpRequest.getEmail())) {
                    return ResponseEntity.ok().body(new IGenericResponse(200, "Email has been used"));
                }
                if (account.getPhoneNumber().equals(signUpRequest.getPhoneNumber())) {
                    return ResponseEntity.ok().body(new IGenericResponse(200, "PhoneNumber has been used"));
                }
            } else {
                account = new Account();
            }

             account = new Account(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getPhoneNumber(),
                    signUpRequest.getFullName()
            );
            account.setCreateDate(LocalDateTime.now());
            account.setStatus(0);
            if (signUpRequest.getRole().equalsIgnoreCase(String.valueOf(ERoleName.Admin))) {
                account.setRole(ERoleName.Admin);
            } else {
                account.setRole(ERoleName.User);
            }
            Random rand = new Random();
            int code = rand.nextInt(999999 - 100000 + 1) + 100000;
            account.setVerificationCode(code);
            account.setTimeValid(LocalDateTime.now().plusMinutes(30));
            TwilioSendSms twilioSendSms = new TwilioSendSms();
            twilioSendSms.sendCode(account.getPhoneNumber(), code);
            account.setPriority(0.0);
            Account account1 = accountService.save(account);

            AccountDto accountDto = modelMapper.map(account1, AccountDto.class);

            return ResponseEntity.ok().body(new IGenericResponse(accountDto, 200, "sign up successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("logout")
    public ResponseEntity<?> logout(@RequestParam("token") String token) {
        try {
            Optional<UserInfo> userInfo = userInfoRepository.findByToken(token);
            if (userInfo.isPresent()) {
                userInfo.get().setStatus(0);
                return ResponseEntity.ok().body(new IGenericResponse<>(userInfoRepository.save(userInfo.get()), 200, ""));
            }
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "not found"));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));

        }
    }

    @GetMapping("forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email,
                                            @RequestParam("password") String password,
                                            @RequestParam("confirm") String confirm) {
        try {
            Optional<Account> accountOptional = accountService.findByEmail(email);
            if (accountOptional.isPresent()) {
                if (password.equals(confirm)) {
                    accountOptional.get().setPassword(passwordEncoder.encode(password));

                    return ResponseEntity.ok().body(new IGenericResponse<>(accountService.save(accountOptional.get()), 200, ""));
                } else {
                    return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "confirm is not equal password"));

                }

            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy account"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("verify")
    public ResponseEntity<?> verify(@RequestParam("code") String code, @RequestParam("id") Integer id) {
        try {
            Optional<Account> accountOptional = accountService.findById(id);
            if (accountOptional.isPresent()) {
                if (accountOptional.get().getVerificationCode().equals(code)
                        && accountOptional.get().getTimeValid().isBefore(LocalDateTime.now())) {
                    accountOptional.get().setStatus(1);
                    return ResponseEntity.ok().body(new IGenericResponse<>(accountService.save(accountOptional.get()), 200, "thành công"));
                }
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "code không đúng hoặc quá hạn"));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
}
