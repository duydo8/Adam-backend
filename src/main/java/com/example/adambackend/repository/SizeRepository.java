package com.example.adambackend.repository;

import com.example.adambackend.entities.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Integer> {
    @Query(value = "select * from sizes s join detail_products dp on dp.size_id= s.id where dp.id = ?1 and dp.status = 1 and" +
            " s.status = 1 ", nativeQuery = true)
    Optional<Size> findByDetailProductId(Integer detailProductId);

    @Query(value = "select s from Size s where s.status = 1 and (:name is null or s.sizeName like concat('%',:name,'%')) order by s.createDate desc")
    List<Size> findAll(@Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "update sizes set status = 0 where id = ?1", nativeQuery = true)
    void updateSizeDeleted(Integer id);

    @Query(value = "select * from sizes where status = 1 order by create_date desc", nativeQuery = true)
    List<Size> findAll();

    @Query("select s from Size s where s.sizeName = ?1 and s.status = 1")
    Size findByName(String name);

}
