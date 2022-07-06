package com.example.adambackend.repository;

import com.example.adambackend.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
    @Query(value = "select * from sizes s join detail_products dp on dp.size_id= s.id where dp.id=?1", nativeQuery = true)
   Size findByDetailProductId(Integer detailProductId);
    @Modifying
    @Transactional
    @Query(value = "update sizes set is_deleted=1 and is_active=0 where id=?1",nativeQuery = true)
    void updateProductsDeleted(Integer id);
}
