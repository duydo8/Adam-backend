package com.example.adambackend.repository;

import com.example.adambackend.entities.DetailProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailProductRepository extends JpaRepository<DetailProduct, Long> {
    @Query("select dp from DetailProduct  dp join Product p on dp.product.id=p.id where p.id=?1")
    List<DetailProduct> findAllByProductId(Long id);
}
