package com.example.adambackend.controller.general;


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
import net.bytebuddy.utility.RandomString;
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
    private  int expirationDateInMS;

    @PostMapping("authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AccountLoginRequestDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            AccountDetailsService userDetails = (AccountDetailsService) authentication.getPrincipal();
            ERoleName roles = accountRepository.findByUsername(loginRequest.getUsername()).get().getRole();

            List<UserInfo> userInfoList= userInfoRepository.findAll();
            if(userInfoList.size()>0){
                boolean check= false;
                for (UserInfo u: userInfoList
                     ) {
                    if(u.getUsername().equals(loginRequest.getUsername())){
                        u.setToken(jwt);
                        u.setTimeValid(LocalDateTime.now().plusSeconds(expirationDateInMS/1000));
                        u.setIsDeleted(false);
                        userInfoRepository.save(u);
                        check=true;
                        break;
                    }
                }
                if(check=false){
                    UserInfo userInfo= new UserInfo();
                    userInfo.setUsername(loginRequest.getUsername());
                    userInfo.setToken(jwt);
                    userInfo.setTimeValid(LocalDateTime.now().plusSeconds(expirationDateInMS/1000));
                    userInfo.setIsDeleted(false);
                    userInfoRepository.save(userInfo);

                }

            }
            UserInfo userInfo= new UserInfo();
            userInfo.setUsername(loginRequest.getUsername());
            userInfo.setToken(jwt);
            userInfo.setTimeValid(LocalDateTime.now().plusSeconds(expirationDateInMS/1000));
            userInfo.setIsDeleted(false);
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
            if (accountService.findByPhoneNumber(signUpRequest.getPhoneNumber()).isPresent()) {
                return ResponseEntity
                        .badRequest()
                        .body(new IGenericResponse(400, "PhoneNumber has been used"));
            }


            Account account = new Account(signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    passwordEncoder.encode(signUpRequest.getPassword()), signUpRequest.getPhoneNumber(),
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
            int code = new Random().nextInt(999999);
            account.setVerificationCode(code);
            account.setTimeValid(LocalDateTime.now().plusMinutes(30));
            TwilioSendSms twilioSendSms = new TwilioSendSms();
            twilioSendSms.sendCode(account.getPhoneNumber(), code);
            account.setPriority(0.0);
            Account account1 = accountService.save(account);

            AccountDto accountDto = modelMapper.map(account1, AccountDto.class);

            return ResponseEntity.ok().body(new IGenericResponse(accountDto, 200, "sign up succrssfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
    @GetMapping("logout")
    public ResponseEntity<?> logout(@RequestParam("token")String token){
        try{
            Optional<UserInfo> userInfo= userInfoRepository.findByToken(token);
            if(userInfo.isPresent()){
                userInfo.get().setIsDeleted(true);
                return ResponseEntity.ok().body(new IGenericResponse<>(userInfoRepository.save(userInfo.get()),200,""));
            }
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "not found"));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));

        }
    }
    @PostMapping("forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("username")String username,
                                  @RequestParam("phone_number")String phoneNumber,
                                  @RequestParam("code")String code){
        Optional<Account> accountOptional= accountService.findByUsername(username);
        if(accountOptional.isPresent()&& accountOptional.get().getPhoneNumber().equals(phoneNumber)){
            int code1 = new Random().nextInt(999999);
            accountOptional.get().setVerificationCode(code1);
            accountOptional.get().setTimeValid(LocalDateTime.now().plusMinutes(30));
            TwilioSendSms twilioSendSms = new TwilioSendSms();
            twilioSendSms.sendCode(accountOptional.get().getPhoneNumber(), code1);
            return ResponseEntity.ok(new IGenericResponse<>
                    ("code đã được gửi hiệu lực trong 30p",200,""));
        }else {
            return ResponseEntity.badRequest().body(new IGenericResponse<>("ko tìm thấy", 400, ""));
        }
    }
    @GetMapping("verify")
    public ResponseEntity<?> verify(@RequestParam("code") String code, @RequestParam("id") Integer id) {
        try {
            Optional<Account> accountOptional = accountService.findById(id);
            if (accountOptional.isPresent()) {
                if (accountOptional.get().getVerificationCode().equals(code)
                        && accountOptional.get().getTimeValid().isBefore(LocalDateTime.now())  ) {
                    accountOptional.get().setIsActive(true);
                    ;
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
