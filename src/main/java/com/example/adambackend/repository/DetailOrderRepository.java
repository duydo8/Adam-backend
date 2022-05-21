package com.example.adambackend.repository;

import com.example.adambackend.enums.entities.DetailOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrder, Long> {
}
