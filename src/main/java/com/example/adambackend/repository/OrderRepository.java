package com.example.adambackend.repository;

import com.example.adambackend.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("select o from Order  o join  Account  a on a.id=o.account.id where a.id=?1 order by o.timeCreate")
    List<Order> findTop5OrderByCreateTime(Long accountId);
}
