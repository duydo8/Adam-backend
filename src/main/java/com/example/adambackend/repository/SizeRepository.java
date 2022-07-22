package com.example.adambackend.repository;

import com.example.adambackend.entities.Category;
import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
    @Query(value = "select * from sizes s join detail_products dp on dp.size_id= s.id where dp.id=?1 and" +
            " s.is_active=1 and s.is_deleted=0 ", nativeQuery = true)
    Optional<Size> findByDetailProductId(Integer detailProductId);
    @Query(value = "select c from Size c where c.sizeName like concat('%',:name,'%') ")
    List<Size> findByName(@Param("name")  String name);
    @Modifying
    @Transactional
    @Query(value = "update sizes set is_deleted=1 , is_active=0 where id=?1", nativeQuery = true)
    void updateProductsDeleted(Integer id);

    @Query(value = "select * from sizes where is_active=1 and is_deleted=0", nativeQuery = true)
    List<Size> findAlls();
}
