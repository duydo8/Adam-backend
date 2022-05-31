package com.example.adambackend.repository;

import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrder, Long> {
    @Query("select do from DetailOrder do join Order o on o.id=do.order.id where o.id=?1")
    List<DetailOrder> findAllByOrderId(Long orderId);

    @Query(value = "select top(10) p from Detail_Orders  do join Detail_Products dp on do.detail_Product_id=dp.id join Products p on p.id=dp.product_id group by do.detail_Product_id" +
            ",dp.id,p.id,dp.product_id order by count(do.quantity) DESC ", nativeQuery = true)
    List<Product> findTop10ProductByCountQuantityInOrderDetail();
}
