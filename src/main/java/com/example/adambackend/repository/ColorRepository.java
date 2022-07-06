package com.example.adambackend.repository;

import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    @Query(value = "select * from colors c join detail_products dp on dp.color_id= c.id " +
            "where dp.id=?1 and  c.is_active=1 and c.is_deleted=0 ", nativeQuery = true)
    Color findByDetailProductId(Integer detailProductId);
    @Modifying
    @Transactional
    @Query(value = "update colors set is_deleted=1 and is_active=0 where id=?1",nativeQuery = true)
    void updateColorsDeleted(Integer id);
    @Query(value = "select * from colors where is_active=1 and is_deleted=0",nativeQuery = true)
    List<Color> findAlls();
}
