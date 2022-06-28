package com.example.adambackend.repository;

import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer> {
    @Query(value = "select * from Colors c join detail_products dp on dp.color_id= c.id where dp.id=?1",nativeQuery = true)
    List<Color> findByDetailProductId(Integer detailProductId);
}
