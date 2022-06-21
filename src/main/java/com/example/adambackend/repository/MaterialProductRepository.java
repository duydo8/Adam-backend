package com.example.adambackend.repository;

import com.example.adambackend.entities.MaterialProduct;
import com.example.adambackend.entities.MaterialProductPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialProductRepository extends JpaRepository<MaterialProduct, MaterialProductPK> {
}
