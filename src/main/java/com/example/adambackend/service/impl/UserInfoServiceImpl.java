package com.example.adambackend.service.impl;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.UserInfo;
import com.example.adambackend.repository.UserInfoRepository;
import com.example.adambackend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Override
	public UserInfo save(UserInfo userInfo){
		return userInfoRepository.save(userInfo);
	}

	@Override
	public List<UserInfo> findAll(){
		return userInfoRepository.findAll();
	}

	@Override
	public Optional<UserInfo> findByToken(String token){
		if(CommonUtil.isNotNull(token)){
			return null;
		}
		return userInfoRepository.findByToken(token);
	}

}
