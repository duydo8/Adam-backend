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
@CrossOrigin(value = "*", maxAge = 3600)
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
			account.setUsername(signUpRequest.getUsername());
			account.setEmail(signUpRequest.getEmail());
			account.setFullName(signUpRequest.getFullName());
			account.setCreateDate(LocalDateTime.now());
			account.setStatus(1);
			account.setPriority(0.0);
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
					Account account1 = accountService.save(account);
					AccountDto accountDto = modelMapper.map(account1, AccountDto.class);
					return ResponseEntity.ok().body(new IGenericResponse(accountDto, 200, "successfully"));
				}
				return ResponseEntity.badRequest().body(new IGenericResponse("", 400, "Vui lòng nhập lại"));
			}
			accountService.deleteById(account.getId());
			return ResponseEntity.badRequest().body(new IGenericResponse(" ", 400, "Đã quá thời gian chờ"));
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
			Random rand = new Random();
			int code = rand.nextInt(999999 - 100000 + 1) + 100000;
			phoneNumber = phoneNumber.substring(1, phoneNumber.length());
			twilioSendSms.sendCode(phoneNumber, code);
			Account account = new Account();
			account.setPhoneNumber(phoneNumber);
			account.setUsername(phoneNumber);
			account.setRole(ERoleName.User);
			account.setStatus(0);
			account.setPassword(passwordEncoder.encode("123456"));
			account.setVerificationCode(code);
			account.setTimeValid(LocalDateTime.now().plusMinutes(30));
			accountService.save(account);
			return ResponseEntity.ok().body(new IGenericResponse(code, 200, "Thành công"));
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
				TwilioSendSms twilioSendSms = new TwilioSendSms();
				Random rand = new Random();
				int code = rand.nextInt(999999 - 100000 + 1) + 100000;
				twilioSendSms.sendCode(phoneNumber, code);
				accountOptional.get().setVerificationCode(code);
				accountOptional.get().setTimeValid(LocalDateTime.now().plusMinutes(30));
				accountService.save(accountOptional.get());
				return ResponseEntity.ok().body(new IGenericResponse<>(code, 200, "thanh cong"));
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
			Optional<Account> accountOptional = accountService.findByPhoneNumber(phoneNumber);
			if (accountOptional.isPresent()) {
				if (code == accountOptional.get().getVerificationCode()) {
					if (LocalDateTime.now().isBefore(accountOptional.get().getTimeValid())) {
						if (password.equals(confirm)) {
							accountOptional.get().setPassword(passwordEncoder.encode(password));
							return ResponseEntity.ok().body(new IGenericResponse<>(accountService.save(accountOptional.get()), 200, "Thành công"));
						} else {
							return ResponseEntity.ok().body(new IGenericResponse<>(200, "Confirm ko giống pass"));
						}
					} else {
						return ResponseEntity.ok().body(new IGenericResponse(400, "Quá hạn"));
					}
				} else {
					return ResponseEntity.ok().body(new IGenericResponse(400, " code không đúng "));
				}
			} else {
				return ResponseEntity.ok().body(new IGenericResponse(400, "Không tìm thấy account"));
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
