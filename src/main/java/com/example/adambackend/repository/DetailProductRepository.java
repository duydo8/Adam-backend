package com.example.adambackend.repository;

import com.example.adambackend.entities.DetailProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DetailProductRepository extends JpaRepository<DetailProduct, Integer> {
    @Query(value = "select * from detail_products  dp join products p on dp.product_id=p.id " +
            "where p.id=?1 and dp.is_active=true and dp.is_deleted=false and p.is_active=true and p.is_deleted=false",nativeQuery = true)
    List<DetailProduct> findAllByProductId(Integer id);

    @Transactional
    @Modifying
    @Query(value = "delete from detail_products where size_id=?1", nativeQuery = true)
    Integer deleteBySizeId(Integer sizeId);

    @Transactional
    @Modifying
    @Query(value = "delete from detail_products where color_id=?1", nativeQuery = true)
    Integer deleteByColorId(Integer colorId);

    @Transactional
    @Modifying
    @Query(value = "delete from detail_products where product_id=?1", nativeQuery = true)
    void deleteByProductId(Integer productId);

    @Modifying
    @Transactional
    @Query(value = "update detail_products set is_deleted=1 , is_active=0 where id=?1",nativeQuery = true)
    void updateDetailProductsDeleted(Integer id);

}
