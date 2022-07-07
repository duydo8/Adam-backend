package com.example.adambackend.repository;

import com.example.adambackend.entities.Account;
import com.example.adambackend.payload.account.AccountResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    @Query(value = "select * from accounts where phone_number=?1",nativeQuery = true)
  Optional<Account> findByPhoneNumber(String phoneNumber);

    @Query(value = "select * from Account a where a.roleName=?1", nativeQuery = true)
    List<Account> findByRoleName(String role);

    @Query("SELECT a FROM Account a WHERE a.verificationCode = ?1")
    public Account findByVerificationCode(String code);

    @Query(value = "select id as id, username as username, full_name as fullName, email as email, phone_number as phoneNumber, password as password, " +
            "role as role, is_active as isActive, is_deleted as isDeleted, priority as priority from accounts where is_active=1 and is_deleted=0", nativeQuery = true)
    public List<AccountResponse> findAlls();
    @Query(value = "select count(*) from accounts where is_active=1 and is_deleted=0 and month(create_date)=?1 and year(create_date)=2022 ",nativeQuery = true)
    Double countTotalAccount(Integer month);
    @Query(value = "select count(*) from accounts where month(create_date)=?1 and year(create_date)=2022 ",nativeQuery = true)
    Double countTotalSignUpAccount(Integer month);
    @Query(value = "select count(a.id) from accounts a where a.id in (select account_id from orders) and month(create_date)=?1 and year(create_date)=2022 ",nativeQuery = true)
    Double countTotalAccountInOrder(Integer month);
    @Modifying
    @Transactional
    @Query(value = "update accounts set is_deleted=1 , is_active=0 where id=?1",nativeQuery = true)
    void updateAccountDeleted(Integer id);

}
