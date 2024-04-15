package com.example.adambackend.service;

import com.example.adambackend.entities.Account;
import com.example.adambackend.payload.account.AccountDTOs;
import com.example.adambackend.payload.account.AccountResponse;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface AccountService {
    Optional<Account> findByUsername(String username);
    List<AccountResponse> findAll();
    Account save(Account account);
    void deleteById(Integer id);
    Optional<Account> findById(Integer id);
    List<Account> findByRoleName(String roleName);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    AccountDTOs findByIds(Integer id);
    void sendVerificationEmail(Account account, String siteURL) throws MessagingException, UnsupportedEncodingException;
    Optional<Account> findByEmail(String email);
    Optional<Account> findByPhoneNumber(String phoneNumber);
    List<Double> countTotalAccount(Integer year);
    List<Double> countTotalSignUpAccount(Integer year);
    List<Double> countTotalAccountInOrder(Integer year);
    void updateAccountDeleted(String ids);
    Account getById(Integer id);
    Account findByUserNameAndEmailAndPhoneNumber(String username, String email, String phoneNumber);

    String checkAccountRegistration (String username, String email, String phoneNumber);
}
