package com.example.adambackend.repository;

import com.example.adambackend.entities.Material;
import com.example.adambackend.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository  extends JpaRepository<Material, Integer> {
}
