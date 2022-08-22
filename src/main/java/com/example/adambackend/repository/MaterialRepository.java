package com.example.adambackend.repository;

import com.example.adambackend.entities.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {
    @Transactional
    @Modifying
    @Query(value = "update materials set is_active=0 , is_deleted=1 where id=?1", nativeQuery = true)
    void updateDeleteByArrayId(Integer id);

    @Query(value = "select * from materials where is_active=1 and is_deleted=0", nativeQuery = true)
    List<Material> findAll();

    @Query(value = "select c from Material c where c.isActive=true and c.isDeleted=false and  c.materialName like concat('%',:name,'%') ")
    List<Material> findAll(@Param("name") String name);
}
