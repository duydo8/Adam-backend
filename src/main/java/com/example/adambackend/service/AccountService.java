package com.example.adambackend.service;

import com.example.adambackend.entities.Account;
import com.example.adambackend.payload.AccountResponse;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<AccountResponse> findAll();

    Account save(Account account);

    void deleteById(Integer id);

    Optional<Account> findById(Integer id);

    List<Account> findByRoleName(String roleName);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    void register(Account account, String siteURL) throws UnsupportedEncodingException, MessagingException;

    void sendVerificationEmail(Account account, String siteURL) throws MessagingException, UnsupportedEncodingException;



    Optional<Account> findByEmail(String email);

    Optional<Account> findByPhoneNumber(String phoneNumber);
}
