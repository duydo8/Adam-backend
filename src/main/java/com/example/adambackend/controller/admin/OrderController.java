package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.*;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.order.Dashboard;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.HistoryOrderRepository;
import com.example.adambackend.repository.OrderRepository;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.DetailOrderService;
import com.example.adambackend.service.DetailProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 36000000)
@RequestMapping("admin/order")
public class OrderController {

    private final List<String> thang = Arrays.asList("January", "February", "March", "April", "May",
            "June", "July", "August", "September", "October", "November", "December");
    @Autowired
    OrderRepository orderService;
    @Autowired
    DetailProductService detailProductService;
    @Autowired
    AccountService accountService;
    @Autowired
    DetailOrderService detailOrderService;
    @Autowired
    HistoryOrderRepository historyOrderRepository;

    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page, @RequestParam("size") int size,
                                              @RequestParam(value = "status", required = false) Integer status) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").ascending());
            Page<Order> page1 = orderService.findByStatus(status, pageable);
            return ResponseEntity.ok().body(new IGenericResponse<Page<Order>>(page1, 200, "Page product"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PutMapping("updateByIdAndStatus")
    public ResponseEntity<?> update(@RequestParam("order_id") Integer orderId,
                                    @RequestParam("status") Integer status) {
        try {
            Optional<Order> orderOptional = orderService.findById(orderId);
            if (orderOptional.isPresent()) {
                orderOptional.get().setStatus(status);
                List<DetailOrder> detailOrderList = orderOptional.get().getDetailOrders();

                return ResponseEntity.ok().body(new IGenericResponse<Order>(orderService.save(orderOptional.get()), 200, ""));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy event"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody Order order) {
        try {
            Optional<Order> orderOptional = orderService.findById(order.getId());
            if (orderOptional.isPresent()) {

                HistoryOrder historyOrder = new HistoryOrder();
                order = orderService.save(order);

                historyOrder.setOrder(orderService.findById(order.getId()).get());
                historyOrder.setDescription("create time");
                historyOrder.setUpdateTime(LocalDateTime.now());
                historyOrder.setIsActive(true);
                historyOrder.setTotalPrice(order.getTotalPrice());
                historyOrder.setStatus(6);
                historyOrder = historyOrderRepository.save(historyOrder);
                List<HistoryOrder> historyOrders = order.getHistoryOrders();
                historyOrders.add(historyOrder);
                order.setHistoryOrders(historyOrders);

                return ResponseEntity.ok().body(new IGenericResponse<Order>(orderService.save(order), 200, ""));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy event"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PutMapping("updateSuccess")
    public ResponseEntity<?> updateEventSuccess(@RequestParam("orderId") Integer orderId) {
        try {
            Optional<Order> orderOptional = orderService.findById(orderId);
            if (orderOptional.isPresent()) {
                List<DetailOrder> detailOrderList = orderOptional.get().getDetailOrders();
                orderOptional.get().setStatus(6);
                for (DetailOrder detailOrder : detailOrderList
                ) {
                    Integer detailProductId = detailOrder.getDetailProduct().getId();
                    Integer quantity = detailOrder.getQuantity();
                    Optional<DetailProduct> detailProductOptional = detailProductService.findById(detailProductId);
                    detailProductOptional.get().setQuantity(detailProductOptional.get().getQuantity() - quantity);
                    detailProductService.save(detailProductOptional.get());

                }
                Account account = accountService.findById(orderOptional.get().getAccount().getId()).get();
                if (account.getPriority() == 10) {

                } else {
                    account.setPriority(account.getPriority());
                }
                Order order = orderOptional.get();
                HistoryOrder historyOrder = new HistoryOrder();
                order = orderService.save(order);

                historyOrder.setOrder(orderService.findById(order.getId()).get());
                historyOrder.setDescription("create time");
                historyOrder.setUpdateTime(LocalDateTime.now());
                historyOrder.setIsActive(true);
                historyOrder.setTotalPrice(order.getTotalPrice());
                historyOrder.setStatus(6);
                historyOrder = historyOrderRepository.save(historyOrder);
                List<HistoryOrder> historyOrders = new ArrayList<>();
                historyOrders.add(historyOrder);
                order.setHistoryOrders(historyOrders);
                orderService.save(order);
                return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));

            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy "));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi... "));
        }
    }


    @DeleteMapping("delete")
    public ResponseEntity<?> deleteOrder(@RequestParam("order_id") Integer orderId) {
        try {
            Optional<Order> optionalOrder = orderService.findById(orderId);
            if (optionalOrder.isPresent()) {
                detailOrderService.deleteAllByOrderId(orderId);
                orderService.deleteById(orderId);
                return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy Order"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("orderSatistic")
    public ResponseEntity<?> sumTotalPriceByTime() {
        try {
            List<Dashboard> dashboardList = new ArrayList<>();
            Dashboard dashboard = new Dashboard();
            dashboard.setName("Tổng đơn hàng");

            List<Double> doubleList = Arrays.asList(orderService.sumTotalPriceByTime(1),
                    orderService.sumTotalPriceByTime(2),
                    orderService.sumTotalPriceByTime(3),
                    orderService.sumTotalPriceByTime(4),
                    orderService.sumTotalPriceByTime(5),
                    orderService.sumTotalPriceByTime(6),
                    orderService.sumTotalPriceByTime(7),
                    orderService.sumTotalPriceByTime(8),
                    orderService.sumTotalPriceByTime(9),
                    orderService.sumTotalPriceByTime(10),
                    orderService.sumTotalPriceByTime(11),
                    orderService.sumTotalPriceByTime(12)

            );

            dashboard.setData(doubleList);
            dashboard.setLabels(thang);


            Dashboard dashboard1 = new Dashboard();
            dashboard1.setName("Đơn thành công");


            List<Double> doubleList1 = Arrays.asList(orderService.sumSuccessOrderByTime(1),
                    orderService.sumSuccessOrderByTime(2),
                    orderService.sumSuccessOrderByTime(3),
                    orderService.sumSuccessOrderByTime(4),
                    orderService.sumSuccessOrderByTime(5),
                    orderService.sumSuccessOrderByTime(6),
                    orderService.sumSuccessOrderByTime(7),
                    orderService.sumSuccessOrderByTime(8),
                    orderService.sumSuccessOrderByTime(9),
                    orderService.sumSuccessOrderByTime(10),
                    orderService.sumSuccessOrderByTime(11),
                    orderService.sumSuccessOrderByTime(12)
            );
            dashboard1.setData(doubleList1);
            dashboard1.setLabels(thang);
            Dashboard dashboard2 = new Dashboard();
            dashboard2.setName("Đơn Hủy");

            List<Double> doubleList2 = Arrays.asList(orderService.sumCancelOrderByTime(1),
                    orderService.sumCancelOrderByTime(2),
                    orderService.sumCancelOrderByTime(3),
                    orderService.sumCancelOrderByTime(4),
                    orderService.sumCancelOrderByTime(5),
                    orderService.sumCancelOrderByTime(6),
                    orderService.sumCancelOrderByTime(7),
                    orderService.sumCancelOrderByTime(8),
                    orderService.sumCancelOrderByTime(9),
                    orderService.sumCancelOrderByTime(10),
                    orderService.sumCancelOrderByTime(11),
                    orderService.sumCancelOrderByTime(12)
            );
            dashboard2.setData(doubleList2);

            dashboard2.setLabels(thang);
            Dashboard dashboard3 = new Dashboard();
            dashboard3.setName("Đơn Đổi Trả");

            List<Double> doubleList3 = Arrays.asList(orderService.sumPaybackOrderByTime(1),
                    orderService.sumPaybackOrderByTime(2),
                    orderService.sumPaybackOrderByTime(3),
                    orderService.sumPaybackOrderByTime(4),
                    orderService.sumPaybackOrderByTime(5),
                    orderService.sumPaybackOrderByTime(6),
                    orderService.sumPaybackOrderByTime(7),
                    orderService.sumPaybackOrderByTime(8),
                    orderService.sumPaybackOrderByTime(9),
                    orderService.sumPaybackOrderByTime(10),
                    orderService.sumPaybackOrderByTime(11),
                    orderService.sumPaybackOrderByTime(12)

            );
            dashboard3.setData(doubleList3);

            dashboard3.setLabels(thang);
            dashboardList.add(dashboard);
            dashboardList.add(dashboard1);
            dashboardList.add(dashboard2);
            dashboardList.add(dashboard3);
            return ResponseEntity.ok().body(new IGenericResponse<>(dashboardList, 200, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }


}
