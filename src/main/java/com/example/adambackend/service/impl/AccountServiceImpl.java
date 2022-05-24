package com.example.adambackend.service.impl;

import com.example.adambackend.enums.entities.Account;
import com.example.adambackend.repository.AccountRepository;
import com.example.adambackend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;
    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account create(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void deleteById(Long id) {
    accountRepository.deleteById(id);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public List<Account> findByRoleName(String roleName) {
        return accountRepository.findByRoleName(roleName);
    }

}
