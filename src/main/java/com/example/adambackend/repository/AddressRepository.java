package com.example.adambackend.repository;

import com.example.adambackend.entities.Address;
import com.example.adambackend.entities.District;
import com.example.adambackend.entities.Province;
import com.example.adambackend.entities.Ward;
import com.example.adambackend.payload.address.AddressDTO;
import com.example.adambackend.payload.address.AddressResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
    @Query("select a.id as id,a.addressDetail as addressDetail,a.isDeleted as isDeleted,a.createDate " +
            "as createDate,a.ward as ward,a.isActive as isActive,a.phoneNumber as phoneNumber,a.fullName as fullName,a.isDefault as" +
            " isDefault,a.province as province,a.district as district  from Address a "
//            +   "join Province p on a.province.id=p.id " +
//            "join District d on d.id=a.district.id " +
//            "join Ward w on w.id=a.ward.id"
   +" where a.id=?1" )
    AddressResponse findByAddressId(Integer id);
}
