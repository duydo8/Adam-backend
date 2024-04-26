package com.example.adambackend.repository;

import com.example.adambackend.entities.CartItems;
import com.example.adambackend.payload.cart.CartItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Integer> {
    @Query(value = "select * from cart_items where account_id = ?1 and status = 1 and order_id is null", nativeQuery = true)
    List<CartItems> findCartItemsByAccountId(Integer id);

    @Query(value = "select  * from cart_items where order_id != null and status = 1 and id = ?1", nativeQuery = true)
    Optional<CartItems> findExistOrderById(Integer id);

    @Modifying
    @Transactional
    @Query(value = "update cart_items set status = ?1 where id= ?2", nativeQuery = true)
    void updateStatus(Integer stauts, Integer id);

    @Query(value = "select id as id,quantity as quantity,total_price as totalPrice," +
            "account_id as accountId,detail_product_id as detailProductId, is_active as isActive," +
            "create_date as createDate from cart_items where  status = 1 and order_id=?1", nativeQuery = true)
    List<CartItemResponse> findByOrderId(Integer orderId);

    @Query(value = "select  * from cart_items where order_id is null and status = 1 and id = ?1", nativeQuery = true)
    Optional<CartItems> findById(Integer id);
}
