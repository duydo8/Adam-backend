package com.example.adambackend.controller.website;

import com.example.adambackend.common.CommonUtil;
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
@RequestMapping("account")
public class AccountWebsiteController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ModelMapper modelMapper;

	@PostMapping("/createAccount")
	public ResponseEntity<IGenericResponse> registerUser(@RequestBody SignUpRequest signUpRequest) {
		try {
			String error = accountService.checkAccountRegistration(signUpRequest.getUsername(), signUpRequest.getEmail(),
					signUpRequest.getPhoneNumber());
			if (CommonUtil.isNotNull(error)) {
				return ResponseEntity.ok().body(new IGenericResponse(error, 400, "validate error"));
			}
			Account account = new Account();
			account.setUsername(signUpRequest.getUsername());
			account.setEmail(signUpRequest.getEmail());
			account.setFullName(signUpRequest.getFullName());
			account.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
			if (signUpRequest.getRole().equalsIgnoreCase(String.valueOf(ERoleName.Admin))) {
				account.setRole(ERoleName.Admin);
			} else {
				account.setRole(ERoleName.User);
			}

			if (LocalDateTime.now().isBefore(account.getTimeValid())) {
				System.out.println(account.getVerificationCode());
				System.out.println(signUpRequest.getCode());
				if (account.getVerificationCode() == (signUpRequest.getCode())) {
					account.setTimeValid(null);
					account.setVerificationCode(null);
					account = accountService.save(account);
					AccountDto accountDto = modelMapper.map(account, AccountDto.class);
					return ResponseEntity.ok().body(new IGenericResponse(accountDto, 200, "successfully"));
				}
				return ResponseEntity.badRequest().body(new IGenericResponse(400, "Vui lòng nhập lại"));
			}
			accountService.deleteById(account.getId());
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Đã quá thời gian chờ"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PostMapping("verify")
	public ResponseEntity<?> verify(@RequestParam("phone_number") String phoneNumber) {
		try {
			Optional<Account> accountOptional = accountService.findByPhoneNumber(phoneNumber);
			if (accountOptional.isPresent() && accountOptional.get().getTimeValid() == null) {
				return ResponseEntity.badRequest().body(new IGenericResponse("", 400, "số điện thoại này đã được đăng ký"));
			}
			Integer code = accountService.generateCodeByPhoneNumber(phoneNumber);
			return ResponseEntity.ok().body(new IGenericResponse(code, 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("sendCode")
	public ResponseEntity<?> sendCode(@RequestParam("phone_number") String phoneNumber) {
		try {
			Optional<Account> accountOptional = accountService.findByPhoneNumber(phoneNumber);
			if (accountOptional.isPresent()) {
				int code = accountService.sendCode(accountOptional.get());
				return ResponseEntity.ok().body(new IGenericResponse<>(code, 200, "successfully"));
			}
			return ResponseEntity.ok().body(new IGenericResponse<>(200, "không tìm thấy tài khoản"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Định dạng gửi là +84......."));
		}
	}

	@GetMapping("forgotPassword")
	public ResponseEntity<?> forgotPassword(@RequestParam("phone_number") String phoneNumber,
											@RequestParam("password") String password,
											@RequestParam("confirm") String confirm,
											@RequestParam("code") int code) {
		try {
			String message = accountService.getForgotPasswordResponse(phoneNumber, password, confirm, code);
			return ResponseEntity.ok().body(new IGenericResponse<>(200, message));
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
	public ResponseEntity<?> changePassword(@RequestParam("id") Integer id,
											@RequestParam("password") String password,
											@RequestParam("passwordNew") String passNew,
											@RequestParam("confirm") String confirm) {
		return ResponseEntity.ok().body(new IGenericResponse<>(200, accountService.getMessageChangePassword(id, password, passNew, confirm)));
	}
}
