package com.example.adambackend.repository;

import com.example.adambackend.entities.Address;
import com.example.adambackend.payload.address.AddressDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Query(value = "select * from address where account_id=?1 and is_active=1 and is_deleted=0 ", nativeQuery = true)
    List<Address> findByAccountId(Integer accountId);

    //    @Modifying
//    @Transactional
//    @Query(value = "update address set is_deleted=1 and is_active=0 where id=?1",nativeQuery = true)
//    void updateAddressDeleted(Integer id);
    @Query(value = "select id as id,address_detail as addressDetail,province_id as provinceId," +
            "district_id as districtId,ward_id as wardId," +
            "is_deleted as isDeleted,is_active as isActive,create_date as createDate," +
            "is_default as isDefault,phone_number as phoneNumber,full_name as fullName from address   ",nativeQuery = true)
    List<AddressDTO> findAlls();
}
