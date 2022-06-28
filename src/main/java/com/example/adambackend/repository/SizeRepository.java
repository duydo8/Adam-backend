package com.example.adambackend.repository;

import com.example.adambackend.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
@Query(value = "select * from sizes s join detail_products dp on dp.size_id= s.id where dp.id=?1",nativeQuery = true)
List<Size> findByDetailProductId(Integer detailProductId);
}
