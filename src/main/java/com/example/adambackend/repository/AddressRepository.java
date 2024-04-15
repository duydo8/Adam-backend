package com.example.adambackend.repository;

import com.example.adambackend.entities.Address;
import com.example.adambackend.payload.address.AddressResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
	@Query(value = "select * from address where account_id= ?1 and status = 1 ", nativeQuery = true)
	List<Address> findByAccountId(Integer accountId);

	@Query("select a.id as id,a.addressDetail as addressDetail,a.status as status,a.createDate " +
			"as createDate,a.ward as ward, a.phoneNumber as phoneNumber,a.fullName as fullName,a.isDefault as" +
			" isDefault,a.province as province,a.district as district  from Address a "
//            +   "join Province p on a.province.id=p.id " +
//            "join District d on d.id=a.district.id " +
//            "join Ward w on w.id=a.ward.id"
			+ " where a.id=?1")
	AddressResponse findByAddressId(Integer id);
}
