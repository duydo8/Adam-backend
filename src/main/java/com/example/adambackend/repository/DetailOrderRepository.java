package com.example.adambackend.repository;

import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.detailOrder.DetailOrderPayLoad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrder, Integer> {
	@Query("select do from DetailOrder do join Order o on o.id=do.order.id " +
			"where o.id=?1 and do.status = 1 ")
	List<DetailOrder> findAllByOrderId(Integer orderId);

	@Query(value = "select top(10) p from Detail_Orders  do " +
			"join Detail_Products dp on do.detail_product_id=dp.id " +
			"join Products p on p.id=dp.product_id where p.status = 1 " +
			"group by do.detail_product_id" +
			",dp.id,p.id,dp.product_id order by count(do.quantity) DESC ", nativeQuery = true)
	List<Product> findTop10ProductByCountQuantityInOrderDetail();

	@Query(value = "delete from detail_orders where order_id = ?1", nativeQuery = true)
	void deleteAllByOrderId(Integer orderId);

	@Query(value = "select p.id from detail_orders do join orders o on do.order_id = o.id join detail_products dp on dp.id= do.detail_product_id " +
			"join products p on p.id = dp.product_id where p.status = 1", nativeQuery = true)
	List<Integer> findProductIdByOrder();

	@Transactional
	@Modifying
	@Query(value = "update detail_orders set detail_order_code = ?1 where id = ?2", nativeQuery = true)
	void updateById(String code, Integer id);

	@Query(value = "select * from detail_orders where detail_order_code = ?1", nativeQuery = true)
	DetailOrder findByCode(String code);

	@Transactional
	@Modifying
	@Query(value = "update detail_orders set reason=?1 where id=?2", nativeQuery = true)
	void updateReason(String reason, Integer id);

	@Query(value = "select do.detail_order_code from detail_orders do " +
			"where do.id=?1", nativeQuery = true)
	String findCodeById(Integer id);

	@Query(value = "select new com.example.adambackend.payload.detailOrder.DetailOrderPayLoad(d.id, d.quantity, d.totalPrice," +
			"  d.status, d.createDate, d.detailOrderCode, d.detailProduct) from DetailOrder d where d.order.id = ?1 and d.status = 1")
	List<DetailOrderPayLoad> findByOrderId(Integer orderId);
}
