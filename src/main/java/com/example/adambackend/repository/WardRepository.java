package com.example.adambackend.repository;

import com.example.adambackend.entities.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> {
@Query(value = "select * from wards where district_id=?1",nativeQuery = true)
List<Ward>findByDistrictId(Integer districtId);
}
