package com.example.adambackend.repository;

import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.detailOrder.DetailOrderAdmin;
import com.example.adambackend.payload.detailOrder.DetailOrderDTO;
import com.example.adambackend.payload.detailOrder.DetailOrderPayLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrder, Integer> {
    @Query("select do from DetailOrder do join Order o on o.id=do.order.id " +
            "where o.id=?1 and do.isActive=true and do.isDeleted=false ")
    List<DetailOrder> findAllByOrderId(Integer orderId);

    @Query(value = "select top(10) p from Detail_Orders  do " +
            "join Detail_Products dp on do.detail_Product_id=dp.id " +
            "join Products p on p.id=dp.product_id where and p.is_active=1 and p.is_deleted=0 " +
            "group by do.detail_Product_id" +
            ",dp.id,p.id,dp.product_id order by count(do.quantity) DESC ", nativeQuery = true)
    List<Product> findTop10ProductByCountQuantityInOrderDetail();

    @Query(value = "delete from detail_orders where order_id=?1", nativeQuery = true)
    void deleteAllByOrderId(Integer orderId);

    @Query(value = "select p.id from detail_orders do join orders o on do.order_id=o.id join detail_products dp on dp.id= do.detail_product_id " +
            "join products p on p.id=dp.product_id where p.is_active=1 and p.is_deleted=0", nativeQuery = true)
    List<Integer> findProductIdByOrder();

    @Query(value = "select id as id,quantity as quantity,price as price,total_price as totalPrice," +
            "is_deleted as isDeleted, detail_order_code as detailOrderCode,is_active as isActive," +
            "create_date as createDate,reason as reason,order_id as orderId from detail_orders ", nativeQuery = true)
    List<DetailOrderDTO> findAlls();

    @Transactional
    @Modifying
    @Query(value = "update detail_orders set detail_order_code=?1 where id=?2", nativeQuery = true)
    void updateById(String x, Integer id);

    @Query(value = "select * from detail_orders where detail_order_code=?1", nativeQuery = true)
    DetailOrder findByCode(String code);

    @Transactional
    @Modifying
    @Query(value = "update detail_orders set reason=?1 where id=?2", nativeQuery = true)
    void updateReason(String reason, Integer id);

    @Query(value = "select do.detail_order_code from detail_orders do " +

            "where do.id=?1", nativeQuery = true)
    String findCodeById(Integer id);
    @Query(value = "select new com.example.adambackend.payload.detailOrder.DetailOrderPayLoad(id, quantity, totalPrice," +
            "  status, createDate, detailOrderCode, detailProduct) from DetailOrder where order.id = ?1 and status = 1")
    List<DetailOrderPayLoad> findByOrderId(Integer orderId);
}
