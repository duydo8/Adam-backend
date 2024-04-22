package com.example.adambackend.repository;

import com.example.adambackend.entities.Account;
import com.example.adambackend.payload.account.AccountDTOs;
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
	@Query("select a from Account  a where a.username = ?1 and a.status = 1")
	Optional<Account> findByUsername(String username);

	Optional<Account> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);

	Boolean existsByPhoneNumber(String phoneNumber);

	@Query(value = "select * from accounts where phone_number = ?1 and status = 1", nativeQuery = true)
	Optional<Account> findByPhoneNumber(String phoneNumber);

	@Query(value = "select * from Account a where a.roleName= ?1 and a.status = 1", nativeQuery = true)
	List<Account> findByRoleName(String role);

	@Query("select a from Account a where  a.id= ?1 and a.status = 1")
	Optional<Account> findById(Integer id);

	@Query("SELECT a FROM Account a WHERE a.verificationCode = ?1")
	public Account findByVerificationCode(String code);

	@Query(value = "select new com.example.adambackend.payload.account.AccountResponse(a.id , a.username, a.fullName, a.email," +
			" a.phoneNumber, a.role, a.status, a.priority) from Account a where a.status = 1")
	public List<AccountResponse> findAlls();

	@Query(value = "select count(*) from accounts where status = 1 and year(create_date) = ?1", nativeQuery = true)
	List<Double> countTotalAccount(Integer year);

	@Query(value = "select count(*) from accounts where status = 1 and year(create_date) = ?1", nativeQuery = true)
	List<Double> countTotalSignUpAccount(Integer year);

	@Query(value = "select count(a.id) from accounts a where  a.status = 1 and year(create_date) = ?1 and a.id in (select account_id from orders)  ", nativeQuery = true)
	List<Double> countTotalAccountInOrder(Integer year);

	@Modifying
	@Transactional
	@Query(value = "update accounts set status = 0 where id in ('?1') ", nativeQuery = true)
	void updateAccountDeleted(String id);

	@Query(value = "select a.id as id, a.username as username, a.full_name as fullName, a.email as email, " +
			"a.phone_number as phoneNumber from accounts a where a.id=?1 and a.status = 1", nativeQuery = true)
	AccountDTOs findByIds(Integer id);

	@Query(value = "select a from Account a where a.username = ?1 or a.email = ?2 or a.phoneNumber = ?3 and a.status = 1")
	Account findByUserNameAndEmailAndPhoneNumber(String username, String email, String phoneNumber);
}
