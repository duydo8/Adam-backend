package com.example.adambackend.service;

import com.example.adambackend.enums.entities.Account;

import java.util.List;
import java.util.Optional;

public interface AccountService {

    List<Account> findAll();
    Account create(Account account);
    void deleteById(Long id);
    Optional<Account> findById(Long id);
    List<Account> findByRoleName(String roleName);

}
