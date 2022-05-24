package com.example.adambackend.repository;

import com.example.adambackend.enums.entities.DetailProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailProductRepository extends JpaRepository<DetailProduct, Long> {
}
