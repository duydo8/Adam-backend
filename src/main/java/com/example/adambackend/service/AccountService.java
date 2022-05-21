package com.example.adambackend.service;

import com.example.adambackend.enums.entities.Account;

import java.util.List;

public interface AccountService {

    List<Account> findAll();
}
