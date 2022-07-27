package com.example.adambackend.repository;

import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.DiscountOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository
public interface DiscountOrderRepository extends JpaRepository<DiscountOrder, Integer> {
    @Query("select dp from DiscountOrder dp where  dp.isActive=true and dp.isDeleted=false")
    List<DiscountOrder> findAll();

    @Transactional
    @Modifying
    @Query("update DiscountOrder  dp set dp.isActive=false , dp.isDeleted=true where dp.id=?1")
    void updateIsActive(Integer id);
    @Query("select dos from DiscountOrder dos where dos.event.id=?1")
    List<DiscountOrder> findByEventId(Integer eventId);
    @Query(value = "select c from DiscountOrder c where c.isActive=true and c.isDeleted=false and c.discountName like concat('%',:name,'%') ")
    List<DiscountOrder> findAll(@Param("name")  String name);
    @Query(value = "select * from discount_orders  where order_min_range <?1 and order_max_range > ?1 and " +
            "DATEDIFF(CURRENT_DATE(),start_time)>0 and DATEDIFF(end_time,CURRENT_DATE())>0 " +
            "and is_active=1 and is_deleted=0 and event_id=?2",nativeQuery = true)
    List<DiscountOrder> findByTotalPriceAndTime(Double price,Integer eventId);
}
