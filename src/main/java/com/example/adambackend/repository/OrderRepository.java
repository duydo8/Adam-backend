package com.example.adambackend.repository;

import com.example.adambackend.entities.Order;
import com.example.adambackend.payload.order.OrderFindAll;
import com.example.adambackend.payload.product.CustomProductFilterRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    //    List<Order> findTop5ByOrderLessThanOrderByCreateDateDesc(Integer accountId);
    @Query(value = "select o from Order o where o.account.id=?1 and o.status=?2 order by o.createDate desc ")
    List<Order> findOrderByAccountId(Integer accountId, Integer status);

    @Query(value = "select count(*) from orders where create_date between ?1 and ?2", nativeQuery = true)
    Integer countAllOrderByTime(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select count(*) from orders where status=1 and create_date between ?1 and ?2", nativeQuery = true)
    Integer countCancelOrderByTime(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select count(*) from orders where status=6 and create_date between ?1 and ?2", nativeQuery = true)
    Integer countSuccessOrderByTime(LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "select count(*) from orders where year(create_date) = ?1 ", nativeQuery = true)
    List<Double> sumTotalPriceByTime(Integer year);

    @Query(value = "select count(*) from orders where year(create_date) = ?1 and status=6", nativeQuery = true)
    List<Double> sumSuccessOrderByTime(Integer year);

    @Query(value = "select count(*) from orders where year(create_date) = ?1 and status=-1", nativeQuery = true)
    List<Double> sumPaybackOrderByTime(Integer year);

    @Query(value = "select count(*) from orders where year(create_date) = ?1 and status=0", nativeQuery = true)
    List<Double> sumCancelOrderByTime(Integer year);

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

    @Query(value = "select new com.example.adambackend.payload.order.OrderFindAll(o.id, o.status, o.createDate, o.account.id, " +
            "o.fullName, o.phoneNumber, o.amountPrice, o.salePrice, o.totalPrice, o.address.id, " +
            "o.addressDetail ,o.orderCode ) from Order o where (:status is null or o.status=:status) ")
    List<OrderFindAll> findByStatus(Pageable pageable, @Param("status") Integer status);

    @Query(value = "select count(*) from orders where ?1 is null or status=?1", nativeQuery = true)
    Integer countTotalElementOrder(Integer status);

    @Query(value = "select * from orders where order_code=?1", nativeQuery = true)
    Optional<Order> findByCode(String code);

    @Transactional
    @Modifying
    @Query(value = "update orders set return_order_price=?1,amount_price=?2,total_price=?3,status=?4 where id=?5", nativeQuery = true)
    void updateReturnOrder(Double returnPrice, Double amountPrice, Double totalPrice, Integer status, Integer id);
}

