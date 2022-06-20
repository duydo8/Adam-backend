package com.example.adambackend.security.jwtConfig.repository;

import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrder, Integer> {
    @Query("select do from DetailOrder do join Order o on o.id=do.order.id where o.id=?1")
    List<DetailOrder> findAllByOrderId(Integer orderId);

    @Query(value = "select top(10) p from Detail_Orders  do join Detail_Products dp on do.detail_Product_id=dp.id join Products p on p.id=dp.product_id group by do.detail_Product_id" +
            ",dp.id,p.id,dp.product_id order by count(do.quantity) DESC ", nativeQuery = true)
    List<Product> findTop10ProductByCountQuantityInOrderDetail();
    @Query(value = "delete from detail_orders where order_id=?1",nativeQuery = true)
    void deleteAllByOrderId(Integer orderId);
    @Query(value = "select p.id from detail_orders do join orders o on do.order_id=o.id join detail_products dp on dp.id= do.detail_product_id " +
            "join products p on p.id=dp.product_id",nativeQuery = true)
    List<Integer> findProductIdByOrder();

}
