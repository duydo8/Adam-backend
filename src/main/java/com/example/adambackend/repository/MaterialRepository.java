package com.example.adambackend.repository;

import com.example.adambackend.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    @Transactional
    @Modifying
    @Query(value = "update materials set is_active=0 and is_delete=1 where id=?1", nativeQuery = true)
    void updateDeleteByArrayId(Integer id);
}
