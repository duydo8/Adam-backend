package com.example.adambackend.controller.admin;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.config.TwilioSendSms;
import com.example.adambackend.entities.Account;
import com.example.adambackend.enums.ERoleName;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.account.AccountAdminCreate;
import com.example.adambackend.payload.account.AccountAdminDTO;
import com.example.adambackend.payload.account.AccountArrayId;
import com.example.adambackend.payload.account.AccountResponse;
import com.example.adambackend.payload.satistic.Dashboard;
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
@RequestMapping("/admin/account")
public class AccountController {
	private final List<String> months = Arrays.asList("January", "February", "March", "April", "May",
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

			String error = accountService.checkAccountRegistration(accountAdminCreate.getUsername(),
					accountAdminCreate.getEmail(), accountAdminCreate.getPhoneNumber());
			if(CommonUtil.isNotNull(error)){
				return ResponseEntity.ok().body(new IGenericResponse(200, error));
			}

			Account account  = new Account();
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
			account.setStatus(1);
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
			Random rand = new Random();
			int code = rand.nextInt(999999 - 100000 + 1) + 100000;
			phoneNumber = phoneNumber.substring(1, phoneNumber.length());
			twilioSendSms.sendCode(phoneNumber, code);
			Account account = new Account();
			account.setPhoneNumber(phoneNumber);
			account.setUsername(phoneNumber);
			account.setRole(ERoleName.User);
			account.setPassword(passwordEncoder.encode("123456"));
			account.setVerificationCode(code);
			account.setTimeValid(LocalDateTime.now().plusMinutes(30));
			account.setStatus(1);
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
			Account account = accountService.getById(accountAdminDTO.getId());
			if (CommonUtil.isNotNull(account)) {
				account.setFullName(accountAdminDTO.getFullName());
				account.setEmail(accountAdminDTO.getEmail());
				account.setStatus(accountAdminDTO.getStatus());
				account.setPassword(passwordEncoder.encode(accountAdminDTO.getPassword()));
				accountService.save(account);
				return ResponseEntity.ok().body(new IGenericResponse<>(accountAdminDTO, 200, "success"));
			}
			return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not valid data"));
		}
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> deleteAccount(@RequestParam("id") Integer id) {
		try {
			Optional<Account> accountOptional = accountService.findById(id);
			if (accountOptional.isPresent()) {
				accountService.deleteById(id);
				return ResponseEntity.ok().body(new HandleExceptionDemo(200, "successfully"));
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

	@GetMapping("accountSatistic")
	public ResponseEntity<?> countTotalAccountInOrder() {
		try {
			Dashboard dashboard = new Dashboard();
			dashboard.setName("Tổng số người dùng mua hàng");
			List<Double> doubleList = accountService.countTotalAccountInOrder(LocalDateTime.now().getYear());
			dashboard.setData(doubleList);
			dashboard.setLabels(months);

			Dashboard dashboard1 = new Dashboard();
			dashboard1.setName("Tổng số người dùng ");
			List<Double> doubleList1 = accountService.countTotalAccount(LocalDateTime.now().getYear());
			dashboard1.setData(doubleList1);
			dashboard1.setLabels(months);

			Dashboard dashboard2 = new Dashboard();
			dashboard2.setName("Tổng số người đăng ký mới");
			List<Double> doubleList2 = accountService.countTotalSignUpAccount(LocalDateTime.now().getYear());
			dashboard2.setData(doubleList2);
			dashboard2.setLabels(months);

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
			StringBuilder ids = new StringBuilder();
			for (Integer id : accountIdList) {
				ids.append(id).append(", ");
			}
			ids.deleteCharAt(ids.lastIndexOf(","));
			accountService.updateAccountDeleted(ids.toString());
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
			String passwordEncode = passwordEncoder.encode(passNew);
			account.get().setPassword(passwordEncode);
			accountService.save(account.get());
			return ResponseEntity.ok().body(new IGenericResponse<>("", 200, "Thành công"));
		}
		return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "ko tìm thấy"));
	}

	@PostMapping("/createAdminAccount")
	public ResponseEntity<IGenericResponse> createAdminAccount(@RequestBody AccountAdminCreate accountAdminCreate) {
		try {
			if (accountService.existsByUsername(accountAdminCreate.getUsername())) {
				return ResponseEntity
						.ok()
						.body(new IGenericResponse(200, "UserName has been used"));
			}

			if (accountService.existsByEmail(accountAdminCreate.getEmail())) {
				return ResponseEntity
						.ok()
						.body(new IGenericResponse(200, "Email has been used"));
			}
			Account account = new Account();
			account.setPriority(0.0);
			account.setUsername(accountAdminCreate.getUsername());
			account.setEmail(accountAdminCreate.getEmail());
			account.setRole(ERoleName.Admin);
			account.setEmail(accountAdminCreate.getEmail());
			account.setPassword(passwordEncoder.encode(accountAdminCreate.getPassword()));
			account.setFullName(accountAdminCreate.getFullName());
			account.setCreateDate(LocalDateTime.now());
			account.setPhoneNumber(accountAdminCreate.getPhoneNumber());
			account.setStatus(1);
			return ResponseEntity.ok().body(new IGenericResponse<>(accountService.save(account), 200, "thành công"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

}
