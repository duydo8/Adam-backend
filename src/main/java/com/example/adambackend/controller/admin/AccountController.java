package com.example.adambackend.controller.admin;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Account;
import com.example.adambackend.payload.account.AccountAdminCreate;
import com.example.adambackend.payload.account.AccountAdminDTO;
import com.example.adambackend.payload.account.ListAccountId;
import com.example.adambackend.payload.account.AccountResponse;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/account")
public class AccountController {

	private final List<String> months = Arrays.asList("January", "February", "March", "April", "May",
			"June", "July", "August", "September", "October", "November", "December");

	@Autowired
	private AccountService accountService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/createAccount")
	public ResponseEntity<IGenericResponse> registerUser(@RequestBody AccountAdminCreate accountAdminCreate) {
		try {
			String error = accountService.checkAccountRegistration(accountAdminCreate.getUsername(),
					accountAdminCreate.getEmail(), accountAdminCreate.getPhoneNumber());
			if (CommonUtil.isNotNull(error)) {
				return ResponseEntity.ok().body(new IGenericResponse(200, error));
			}
			Account account = accountService.getAccountFromAccountAdminCreate(accountAdminCreate);
			return ResponseEntity.ok().body(new IGenericResponse(account, 200, "successfully"));
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
				return ResponseEntity.badRequest().body(new IGenericResponse("", 400, "PhoneNumber has been used"));
			}
			Integer code = accountService.generateCodeByPhoneNumber(phoneNumber);
			return ResponseEntity.ok().body(new IGenericResponse(code, 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}


	@GetMapping("findAll")
	public ResponseEntity<?> findAll() {
		try {
			List<AccountResponse> accountList = accountService.findAll();
			return ResponseEntity.ok(new IGenericResponse(accountList, 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
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
				return ResponseEntity.ok().body(new IGenericResponse<>(accountAdminDTO, 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not valid data"));
		}
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> deleteAccount(@RequestParam("id") Integer id) {
		try {
			Optional<Account> accountOptional = accountService.findById(id);
			if (accountOptional.isPresent()) {
				accountService.deleteById(id);
				return ResponseEntity.ok().body(new IGenericResponse(200, "successfully"));
			} else {
				return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findById")
	public ResponseEntity<?> findById(@RequestParam("id") Integer id) {
		try {
			Optional<Account> accountOptional = accountService.findById(id);
			if (accountOptional.isPresent()) {
				return ResponseEntity.ok().body(new IGenericResponse<>(accountOptional.get(), 200, "successfully"));
			} else {
				return ResponseEntity.ok().body(new IGenericResponse(400, "not found"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("accountSatistic")
	public ResponseEntity<?> countTotalAccountInOrder() {
		try {
			return ResponseEntity.ok().body(new IGenericResponse<>(accountService.statisticInAccountAdmin(months), 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("deleteByArrayId")
	public ResponseEntity<?> deleteByArrayId(@RequestBody ListAccountId accountArrayId) {
		try {
			List<Integer> accountIdList = accountArrayId.getAccountIdList();
			StringBuilder ids = new StringBuilder();
			for (Integer id : accountIdList) {
				ids.append(id).append(", ");
			}
			ids.deleteCharAt(ids.lastIndexOf(","));
			accountService.updateAccountDeleted(ids.toString());
			return ResponseEntity.ok().body(new IGenericResponse(200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PostMapping("updatePriority")
	public ResponseEntity<?> updatePriority(@RequestParam("id") Integer id, @RequestParam("priority") Double priority) {
		Optional<Account> account = accountService.findById(id);
		if (account.isPresent()) {
			account.get().setPriority(priority);
			accountService.save(account.get());
			return ResponseEntity.ok().body(new IGenericResponse<>(200, "successfully"));
		}
		return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "not found"));
	}

	@PostMapping("changePassword")
	public ResponseEntity<?> updatePriority(@RequestParam("id") Integer id,
											@RequestParam("password") String password,
											@RequestParam("passwordNew") String passNew,
											@RequestParam("confirm") String confirm) {
		Optional<Account> account = accountService.findById(id);
		if (account.isPresent()) {
			accountService.validateErrorPassword(account.get(), password, passNew, confirm);
			String passwordEncode = passwordEncoder.encode(passNew);
			account.get().setPassword(passwordEncode);
			accountService.save(account.get());
			return ResponseEntity.ok().body(new IGenericResponse<>(200, "successfully"));
		}
		return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "not found"));
	}

	@PostMapping("/createAdminAccount")
	public ResponseEntity<IGenericResponse> createAdminAccount(@RequestBody AccountAdminCreate accountAdminCreate) {
		try {
			String error = accountService.checkAccountRegistration(accountAdminCreate.getUsername(),
					accountAdminCreate.getEmail(), accountAdminCreate.getPhoneNumber());
			if (CommonUtil.isNotNull(error)) {
				return ResponseEntity.ok().body(new IGenericResponse(200, error));
			}
			Account account = accountService.getAccountFromAccountAdminCreate(accountAdminCreate);
			return ResponseEntity.ok().body(new IGenericResponse<>(account, 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}
}