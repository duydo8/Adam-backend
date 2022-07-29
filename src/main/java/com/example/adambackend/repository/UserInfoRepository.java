package com.example.adambackend.repository;

import com.example.adambackend.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Integer> {
    @Query("select u from  UserInfo u where u.token=?1")
    Optional<UserInfo> findByToken(String token);
}
