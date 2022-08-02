package com.example.adambackend.service;

import com.example.adambackend.entities.Account;
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
    Boolean existsByPhoneNumber(String phoneNumber);


    void sendVerificationEmail(Account account, String siteURL) throws MessagingException, UnsupportedEncodingException;


    Optional<Account> findByEmail(String email);

    Optional<Account> findByPhoneNumber(String phoneNumber);

    Double countTotalAccount(Integer month);

    Double countTotalSignUpAccount(Integer month);

    Double countTotalAccountInOrder(Integer month);

    void updateAccountDeleted(Integer id);

}
