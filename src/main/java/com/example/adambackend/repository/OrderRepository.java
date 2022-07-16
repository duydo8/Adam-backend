package com.example.adambackend.repository;

import com.example.adambackend.entities.Order;
import com.example.adambackend.entities.Tag;
import com.example.adambackend.payload.product.CustomProductFilterRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    //    List<Order> findTop5ByOrderLessThanOrderByCreateDateDesc(Integer accountId);
    @Query(value = "select o from Order o where o.account.id=?1 and o.status=?2")
    List<Order> findOrderByAccountId(Integer accountId, Integer status);

    @Query(value = "select count(*) from orders where create_date between ?1 and ?2", nativeQuery = true)
    Integer countAllOrderByTime(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select count(*) from orders where status=1 and create_date between ?1 and ?2", nativeQuery = true)
    Integer countCancelOrderByTime(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select count(*) from orders where status=6 and create_date between ?1 and ?2", nativeQuery = true)
    Integer countSuccessOrderByTime(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select count(*) from orders where month(create_date)=?1 and year(create_date)=2022 ", nativeQuery = true)
    Double sumTotalPriceByTime(Integer month);

    @Query(value = "select count(*) from orders where month(create_date)=?1 and year(create_date)=2022 and status=6", nativeQuery = true)
    Double sumSuccessOrderByTime(Integer month);

    @Query(value = "select count(*) from orders where month(create_date)=?1 and year(create_date)=2022 and status=-1", nativeQuery = true)
    Double sumPaybackOrderByTime(Integer month);

    @Query(value = "select count(*) from orders where month(create_date)=?1 and year(create_date)=2022 and status=0", nativeQuery = true)
    Double sumCancelOrderByTime(Integer month);

    @Query(value = "select o.id from orders o join detail_orders dos on o.id=dos.order_id \n" +
            "join detail_products dp on dp.id=dos.detail_product_id \n" +
            "join products p on dp.product_id=p.id where o.account_id=?1 order by o.create_date desc limit 1", nativeQuery = true)
    Integer findCurrentOrderId(Integer accountId);

    @Query(value = "select p.id as id,p.product_name as productName,p.image as productImage,p.create_date  " +
            "as createDate, MAX(dp.price_export) as maxPrice, MIN(dp.price_export) as minPrice from orders o \n" +
            "join detail_orders dos on o.id=dos.order_id \n" +
            "join detail_products dp on dp.id=dos.detail_product_id \n" +
            "join products p on dp.product_id=p.id \n" +
            "where o.id=?1 GROUP BY p.id,p.product_name,p.image,p.create_date", nativeQuery = true)
    List<CustomProductFilterRequest> findByOrderId(Integer orderId);
    @Query(value = "select * from tags where order_code like '%?1%'",nativeQuery = true)
    List<Order>  findByName(String name);
    @Query(value = "select o from Order o where (o.status=?1 or ?1 is null) ")
    Page<Order> findByStatus(Integer status, Pageable pageable);
}

