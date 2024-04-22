package com.example.adambackend.service;

import com.example.adambackend.entities.Account;
import com.example.adambackend.payload.account.AccountAdminCreate;
import com.example.adambackend.payload.account.AccountAdminDTO;
import com.example.adambackend.payload.account.AccountDTOs;
import com.example.adambackend.payload.account.AccountResponse;
import com.example.adambackend.payload.satistic.Dashboard;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface AccountService {
	List<AccountResponse> findAll();

	Account save(Account account);

	void deleteById(Integer id);

	Optional<Account> findById(Integer id);

	Boolean existsByEmail(String email);

	AccountDTOs findByIds(Integer id);

	public void sendVerificationEmail(Account account, String siteURL) throws MessagingException, UnsupportedEncodingException;

	Optional<Account> findByEmail(String email);

	Optional<Account> findByPhoneNumber(String phoneNumber);

	void updateAccountDeleted(String ids);

	Account getById(Integer id);

	Account findByUserNameAndEmailAndPhoneNumber(String username, String email, String phoneNumber);

	String checkAccountRegistration(String username, String email, String phoneNumber);

	String validateErrorPassword(Account account, String password, String passNew, String passNewConfirm);

	AccountResponse getAccountFromAccountAdminCreate(AccountAdminCreate accountAdminCreate);

	Integer generateCodeByPhoneNumber(String phoneNumber);

    List<Dashboard> statisticInAccountAdmin(List<String> months);

	Optional<Account> findByUsername(String username);

	String getForgotPasswordResponse(String phoneNumber, String password, String confirm, int code);

	String getMessageChangePassword(Integer id, String password, String passNew, String confirm);

	Integer sendCode(Account account);

	Account update(Account account, AccountAdminDTO accountAdminDTO);
}
