package com.example.adambackend.service;

import com.example.adambackend.entities.UserInfo;

import java.util.List;
import java.util.Optional;

public interface UserInfoService {
	UserInfo save(UserInfo userInfo);

	List<UserInfo> findAll();

	Optional<UserInfo> findByToken(String token);
}
