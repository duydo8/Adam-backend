package com.example.adambackend.repository;

import com.example.adambackend.entities.DiscountOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public interface DiscountOrderRepository extends JpaRepository<DiscountOrder, Integer> {
    @Query("select dp from DiscountOrder dp where  dp.status = 1")
    List<DiscountOrder> findAll();

    @Transactional
    @Modifying
    @Query("update DiscountOrder  dp set dp.status = 1 where dp.id=?1")
    void updateStatus(Integer status, Integer id);

    @Query(value = "select c from DiscountOrder c where c.status = 1 and (:name is null or c.discountName like concat('%',:name,'%')) ")
    List<DiscountOrder> findAll(@Param("name") String name);

    @Query(value = "select * from discount_orders  where order_min_range <?1 and order_max_range > ?1 and " +
            "DATEDIFF(CURRENT_DATE(),start_time)>0 and DATEDIFF(end_time,CURRENT_DATE())>0 " +
            "and status = 1 and event_id = ?2", nativeQuery = true)
    List<DiscountOrder> findByTotalPriceAndTime(Double price, Integer eventId);

    @Query("select do from DiscountOrder  do where do.status = 1 and do.event.id = ?1")
    List<DiscountOrder> findByEventId(Integer eventId);

    @Query("select do from DiscountOrder do where do.status = 1 and do.id = ?1 ")
    Optional<DiscountOrder> findById(Integer id);
}
