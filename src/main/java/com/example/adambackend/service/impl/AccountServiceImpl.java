package com.example.adambackend.service.impl;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.config.TwilioSendSms;
import com.example.adambackend.entities.Account;
import com.example.adambackend.enums.ERoleName;
import com.example.adambackend.payload.account.AccountAdminCreate;
import com.example.adambackend.payload.account.AccountDTOs;
import com.example.adambackend.payload.account.AccountResponse;
import com.example.adambackend.payload.satistic.Dashboard;
import com.example.adambackend.repository.AccountRepository;
import com.example.adambackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<AccountResponse> findAll() {
		return accountRepository.findAlls();
	}

	@Override
	public Account save(Account account) {
		return accountRepository.save(account);
	}

	@Override
	public void deleteById(Integer id) {
		accountRepository.deleteById(id);
	}

	@Override
	public Optional<Account> findById(Integer id) {
		return accountRepository.findById(id);
	}

	@Override
	public Boolean existsByEmail(String email) {
		return accountRepository.existsByEmail(email);
	}


	@Override
	public AccountDTOs findByIds(Integer id) {
		return accountRepository.findByIds(id);
	}


	@Override
	public void sendVerificationEmail(Account account, String siteURL) throws MessagingException, UnsupportedEncodingException {
		String toAddress = account.getEmail();
		String fromAddress = "adamstoreservice@gmail.com";
		String senderName = "Adam Store";
		String subject = "Please verify your registration";
		String content = "Dear name,<br>"
				+ "Please click the link below to verify your registration:<br>"
				+ "<h3><a href=\"URL\" target=\"_self\">VERIFY</a></h3>"
				+ "Thank you,<br>"
				+ "Adam Store";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		content = content.replace("name", account.getFullName());
		String verifyURL = siteURL + "/verify?code=" + account.getVerificationCode();

		content = content.replace("URL", verifyURL);

		helper.setText(content, true);

		mailSender.send(message);
	}


	@Override
	public Optional<Account> findByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

	@Override
	public Optional<Account> findByPhoneNumber(String phoneNumber) {
		return accountRepository.findByPhoneNumber(phoneNumber);
	}

	@Override
	public void updateAccountDeleted(String id) {
		accountRepository.updateAccountDeleted(id);
	}

	@Override
	public Account getById(Integer id) {
		return accountRepository.getById(id);
	}

	@Override
	public Account findByUserNameAndEmailAndPhoneNumber(String username, String email, String phoneNumber) {
		return accountRepository.findByUserNameAndEmailAndPhoneNumber(username, email, phoneNumber);
	}

	@Override
	public String checkAccountRegistration(String username, String email, String phoneNumber) {
		Account account = accountRepository.findByUserNameAndEmailAndPhoneNumber(username, email, phoneNumber);
		if (CommonUtil.isNotNull(account)) {
			if (account.getUsername().equals(username)) {
				return "Username has been used";
			}
			if (account.getEmail().equals(email)) {
				return "Email has been used";
			}
			if (account.getPhoneNumber().equals(phoneNumber)) {
				return "PhoneNumber has been used";
			}
		}
		return "";
	}

	@Override
	public String validateErrorPassword(Account account, String password, String passNew, String passNewConfirm) {
		if (!account.getPassword().equals(password)) {
			return "sai mật khẩu";
		}
		if (!passNewConfirm.equals(passNew)) {
			return "Nhập lại mật khẩu không đúng";
		}
		if (passNew.equals(password)) {
			return "Mật khẩu mới giống mật khẩu cũ";
		}
		return "";
	}

	@Override
	public Account getAccountFromAccountAdminCreate(AccountAdminCreate accountAdminCreate) {
		Account account = new Account();
		account.setUsername(accountAdminCreate.getUsername());
		account.setEmail(accountAdminCreate.getEmail());
		account.setPassword(passwordEncoder.encode(accountAdminCreate.getPassword()));
		account.setFullName(accountAdminCreate.getFullName());
		account.setCreateDate(LocalDateTime.now());
		account.setPhoneNumber(accountAdminCreate.getPhoneNumber());
		account.setStatus(1);
		if (accountAdminCreate.getRole().equalsIgnoreCase("admin")) {
			account.setRole(ERoleName.Admin);
		} else {
			account.setRole(ERoleName.User);
		}
		return account;
	}

	@Override
	public Integer generateCodeByPhoneNumber(String phoneNumber) {
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
		account.setStatus(0);
		accountRepository.save(account);
		return code;
	}

	@Override
	public List<Dashboard> statisticInAccountAdmin(List<String> months) {
		Dashboard dashboard = new Dashboard();
		dashboard.setName("Tổng số người dùng mua hàng");
		List<Double> doubleList = accountRepository.countTotalAccountInOrder(LocalDateTime.now().getYear());
		dashboard.setData(doubleList);
		dashboard.setLabels(months);

		Dashboard dashboard1 = new Dashboard();
		dashboard1.setName("Tổng số người dùng ");
		List<Double> doubleList1 = accountRepository.countTotalAccount(LocalDateTime.now().getYear());
		dashboard1.setData(doubleList1);
		dashboard1.setLabels(months);

		Dashboard dashboard2 = new Dashboard();
		dashboard2.setName("Tổng số người đăng ký mới");
		List<Double> doubleList2 = accountRepository.countTotalSignUpAccount(LocalDateTime.now().getYear());
		dashboard2.setData(doubleList2);
		dashboard2.setLabels(months);

		return Arrays.asList(dashboard, dashboard1, dashboard2);
	}

	@Override
	public Optional<Account> findByUsername(String username) {
		return accountRepository.findByUsername(username);
	}

	@Override
	public String getForgotPasswordResponse(String phoneNumber, String password, String confirm, int code) {
		Optional<Account> accountOptional = accountRepository.findByPhoneNumber(phoneNumber);
		if (accountOptional.isPresent() && CommonUtil.isNotNull(accountOptional.get().getVerificationCode())) {
			if (accountOptional.get().getVerificationCode() == code) {
				if (LocalDateTime.now().isBefore(accountOptional.get().getTimeValid())) {
					if (password.equals(confirm)) {
						accountOptional.get().setPassword(passwordEncoder.encode(password));
						accountRepository.save(accountOptional.get());
						return "successfully";
					} else {
						return "Confirm ko giống pass";
					}
				} else {
					return "Quá hạn";
				}
			} else {
				return " code không đúng ";
			}
		} else {
			return "Không tìm thấy account";
		}
	}

	@Override
	public String getMessageChangePassword(Integer id, String password, String passNew, String confirm) {
		Optional<Account> account = accountRepository.findById(id);
		if (account.isPresent()) {
			if (!account.get().getPassword().equals(password)) {
				return "sai mật khẩu";

			}
			if (!confirm.equals(passNew)) {
				return "Nhập lại mật khẩu không đúng";

			}
			if (passNew.equals(password)) {
				return "Mật khẩu mới giống mật khẩu cũ";

			}
			String encode = passwordEncoder.encode(passNew);
			account.get().setPassword(encode);
			accountRepository.save(account.get());
			return "successfully";
		}
		return "not found";
	}

	@Override
	public Integer sendCode(Account account) {
		TwilioSendSms twilioSendSms = new TwilioSendSms();
		Random rand = new Random();
		int code = rand.nextInt(999999 - 100000 + 1) + 100000;
		twilioSendSms.sendCode(account.getPhoneNumber(), code);
		account.setVerificationCode(code);
		account.setTimeValid(LocalDateTime.now().plusMinutes(30));
		accountRepository.save(account);
		return code;
	}
}
