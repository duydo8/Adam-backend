package com.example.adambackend.service;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Address;
import com.example.adambackend.entities.Order;
import com.example.adambackend.payload.order.OrderAdmin;
import com.example.adambackend.payload.order.OrderReturn;
import com.example.adambackend.payload.order.OrderUpdatePayBack;
import com.example.adambackend.payload.order.OrderWebsiteCreate;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.payload.satistic.Dashboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderService {
    List<Order> findAll();

    Order save(Order order);

    void deleteById(Integer id);

    Optional<Order> findById(Integer id);

    Page<Order> findAll(Pageable pageable);

    List<Double> sumTotalPriceByTime(Integer month);

    List<Order> findByAccountId(Integer accountId, Integer status);

    Integer countAllOrderByTime(LocalDateTime startDate, LocalDateTime endDate);

    Integer countCancelOrderByTime(LocalDateTime startDate, LocalDateTime endDate);

    Integer countsuccessOrderByTime(LocalDateTime startDate, LocalDateTime endDate);

    List<Double> sumSuccessOrderByTime(Integer month);

    List<Double> sumCancelOrderByTime(Integer month);

    List<Double> sumPaybackOrderByTime(Integer month);

    boolean getErrorsFindAll(Integer page, Integer size);

    OrderAdmin findAllOrderAdmin(Integer page, Integer size, Integer status);

    IGenericResponse updateStatusOrder(Integer orderId, Integer status);

    Order updateOrder(Order order);

    Order updateSuccess(Order order);

    IGenericResponse getOrderReturn(OrderReturn orderReturn);

    IGenericResponse updateOrderPayBack(OrderUpdatePayBack orderUpdatePayBack);

    List<Order> findOrderByAccountId(Integer accountId, Integer status);

    Double getSalePrice(Double ammountPrice);

    IGenericResponse createOder(OrderWebsiteCreate orderWebsiteCreate, Account account, Address address);

    List<Dashboard> getOrderSatistic(List<String> months);

//    List<Order> findTop5ByOrderLessThanOrderByCreateDateDesc(Integer accountId);
}
