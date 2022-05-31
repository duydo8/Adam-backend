package com.example.adambackend.repository;

import com.example.adambackend.entities.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p")
    List<Product> findAll(Sort sort);
    @Query(value = "select top(10) p from Product p order by p.createDate",nativeQuery = true)
    List<Product> findTop10productByCreateDate();

}
