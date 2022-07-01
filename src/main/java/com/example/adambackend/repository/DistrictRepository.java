package com.example.adambackend.repository;

import com.example.adambackend.entities.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictRepository extends JpaRepository<District, Integer> {
    @Query(value = "select * from districs where province_id=?1",nativeQuery = true)
    List<District> findByProvineId(Integer provinceId);
}
