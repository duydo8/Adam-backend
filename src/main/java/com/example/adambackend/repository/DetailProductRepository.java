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
    @Query("select dp from DetailProduct  dp join Product p on dp.product.id=p.id where p.id=?1")
    List<DetailProduct> findAllByProductId(Integer id);
    @Transactional
    @Modifying
    @Query(value = "delete from detail_products where size_id=?1",nativeQuery = true)
    Integer deleteBySizeId(Integer sizeId);
    @Transactional
    @Modifying
    @Query(value = "delete from detail_products where color_id=?1",nativeQuery = true)
    Integer deleteByColorId(Integer colorId);


}
