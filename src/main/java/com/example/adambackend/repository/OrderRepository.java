package com.example.adambackend.repository;

import com.example.adambackend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

//    List<Order> findTop5ByOrderLessThanOrderByCreateDateDesc(Integer accountId);
@Query(value = "select * from orders where account_id=?1 and status=?2",nativeQuery = true)
List<Order> findOrderByAccountId(Integer accountId , Integer status);
@Query(value = "select count(*) from orders where create_date between ?1 and ?2",nativeQuery = true)
    Integer countAllOrderByTime(LocalDateTime startDate, LocalDateTime endDate);
    @Query(value = "select count(*) from orders where status=1 and create_date between ?1 and ?2",nativeQuery = true)
    Integer countCancelOrderByTime(LocalDateTime startDate, LocalDateTime endDate);
    @Query(value = "select count(*) from orders where status=6 and create_date between ?1 and ?2",nativeQuery = true)
    Integer countSuccessOrderByTime(LocalDateTime startDate, LocalDateTime endDate);
    @Query(value = "select count(*) from orders where month(create_date)=?1 and year(create_date)=2022 ",nativeQuery = true)
    Double sumTotalPriceByTime(Integer month);
    @Query(value = "select count(*) from orders where month(create_date)=?1 and year(create_date)=2022 and status=6",nativeQuery = true)
    Double sumSuccessOrderByTime(Integer month);
    @Query(value = "select count(*) from orders where month(create_date)=?1 and year(create_date)=2022 and status=-1",nativeQuery = true)
    Double sumPaybackOrderByTime(Integer month);
    @Query(value = "select count(*) from orders where month(create_date)=?1 and year(create_date)=2022 and status=0",nativeQuery = true)
    Double sumCancelOrderByTime(Integer month);


}

