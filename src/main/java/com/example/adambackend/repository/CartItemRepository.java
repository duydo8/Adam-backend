package com.example.adambackend.repository;

import com.example.adambackend.entities.CartItems;
import com.example.adambackend.payload.cart.CartItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems, Integer> {
    @Query(value = "select * from cart_items where account_id=?1 and is_active=1 and order_id=null", nativeQuery = true)
    List<CartItems> findCartItemsByAccountId(Integer id);

    @Modifying
    @Transactional
    @Query(value = "update cart_items set is_active=0 where id=?1", nativeQuery = true)
    void updateIsActive(Integer id);
    @Query(value = "select id as id,quantity as quantity,total_price as totalPrice," +
            "account_id as accountId,detail_product_id as detailProductId, is_active as isActive," +
            "create_date as createDate from cart_items where order_id=?1",nativeQuery = true)
    List<CartItemResponse> findByOrderId(Integer orderId);
}
