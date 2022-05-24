package com.example.adambackend.repository;

import com.example.adambackend.enums.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
    @Query(value = "select * from Account a where a.roleName=?1",nativeQuery = true)
    List<Account>  findByRoleName(String role);
}
