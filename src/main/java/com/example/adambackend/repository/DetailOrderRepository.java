package com.example.adambackend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.adambackend.entities.DetailOrder;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrder, Long> {
}
