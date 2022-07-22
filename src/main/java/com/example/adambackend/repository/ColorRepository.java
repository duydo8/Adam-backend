package com.example.adambackend.repository;

import com.example.adambackend.entities.Category;
import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    @Query(value = "select * from colors c join detail_products dp on dp.color_id= c.id " +
            "where dp.id=?1 and  c.is_active=1 and c.is_deleted=0 ", nativeQuery = true)
    Optional<Color> findByDetailProductId(Integer detailProductId);

    @Modifying
    @Transactional
    @Query(value = "update colors set is_deleted=1 , is_active=0 where id=?1", nativeQuery = true)
    void updateColorsDeleted(Integer id);

    @Query(value = "select * from colors where is_active=1 and is_deleted=0", nativeQuery = true)
    List<Color> findAlls();
    @Query(value = "select c from Color c where c.colorName like concat('%',:name,'%') ")
    List<Color> findByName(@Param("name")  String name);
}
