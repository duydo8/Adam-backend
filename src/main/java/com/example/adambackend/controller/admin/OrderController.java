package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.*;
import com.example.adambackend.enums.OrderStatus;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.order.Dashboard;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.HistoryOrderRepository;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.DetailOrderService;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    DetailProductService detailProductService;
    @Autowired
    AccountService accountService;
    @Autowired
    DetailOrderService detailOrderService;
    @Autowired
    HistoryOrderRepository historyOrderRepository;
    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> page1 = orderService.findAll(pageable);
        return ResponseEntity.ok().body(new IGenericResponse<Page<Order>>(page1, 200, "Page product"));
    }

    @PutMapping("updateByIdAndStatus")
    public ResponseEntity<?> update(@RequestParam("order_id") Integer orderId,
                                    @RequestParam("status")Integer status) {
        Optional<Order> orderOptional = orderService.findById(orderId);
        if (orderOptional.isPresent()) {
            orderOptional.get().setStatus(status);

            HistoryOrder historyOrder= new HistoryOrder();


            historyOrder.setOrder(orderOptional.get());
            historyOrder.setDescription("update time");
            historyOrder.setUpdateTime(LocalDateTime.now());
            historyOrder.setIsActive(true);
            historyOrder.setTotalPrice(orderOptional.get().getTotalPrice());
            historyOrder.setStatus(6);
            historyOrder=historyOrderRepository.save(historyOrder);
            List<HistoryOrder> historyOrders= orderOptional.get().getHistoryOrders();
            historyOrders.add(historyOrder);
            orderOptional.get().setHistoryOrders(historyOrders);

            return ResponseEntity.ok().body(new IGenericResponse<Order>(orderService.save(orderOptional.get()), 200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found event"));
        }
    }
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody Order order) {
        Optional<Order> orderOptional = orderService.findById(order.getId());
        if (orderOptional.isPresent()) {

            HistoryOrder historyOrder= new HistoryOrder();
            order=orderService.save(order);

            historyOrder.setOrder(orderService.findById(order.getId()).get());
            historyOrder.setDescription("create time");
            historyOrder.setUpdateTime(LocalDateTime.now());
            historyOrder.setIsActive(true);
            historyOrder.setTotalPrice(order.getTotalPrice());
            historyOrder.setStatus(6);
            historyOrder=historyOrderRepository.save(historyOrder);
            List<HistoryOrder> historyOrders= order.getHistoryOrders();
            historyOrders.add(historyOrder);
            order.setHistoryOrders(historyOrders);

            return ResponseEntity.ok().body(new IGenericResponse<Order>(orderService.save(order), 200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found event"));
        }
    }

    @PutMapping("updateSuccess")
    public ResponseEntity<?> updateEventSuccess(@RequestParam("orderId") Integer orderId) {
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
            Order order= orderOptional.get();
            HistoryOrder historyOrder= new HistoryOrder();
            order=orderService.save(order);

            historyOrder.setOrder(orderService.findById(order.getId()).get());
            historyOrder.setDescription("create time");
            historyOrder.setUpdateTime(LocalDateTime.now());
            historyOrder.setIsActive(true);
            historyOrder.setTotalPrice(order.getTotalPrice());
            historyOrder.setStatus(6);
            historyOrder=historyOrderRepository.save(historyOrder);
            List<HistoryOrder> historyOrders= new ArrayList<>();
            historyOrders.add(historyOrder);
            order.setHistoryOrders(historyOrders);
            orderService.save(order);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));

        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found "));
    }
//    @GetMapping("countAllOrder")
//    public ResponseEntity<?> countAllOrder(@RequestParam("start_date") String startDate,
//                                           @RequestParam("end_date")String endDate){
//        startDate=startDate.replace(" ","T");
//        endDate=endDate.replace(" ","T");
//        LocalDateTime dateStart= LocalDateTime.parse(startDate);
//        LocalDateTime dateEnd=LocalDateTime.parse(endDate);
//        return ResponseEntity.ok().body(new IGenericResponse<>(orderService.countAllOrderByTime(dateStart,dateEnd),200,""));
//    }
//    @GetMapping("countCancelOrder")
//    public ResponseEntity<?> countCancelOrder(@RequestParam("start_date") String startDate,
//                                           @RequestParam("end_date")String endDate){
//        startDate=startDate.replace(" ","T");
//        endDate=endDate.replace(" ","T");
//        LocalDateTime dateStart= LocalDateTime.parse(startDate);
//        LocalDateTime dateEnd=LocalDateTime.parse(endDate);
//        return ResponseEntity.ok().body(new IGenericResponse<>(orderService.countCancelOrderByTime(dateStart,dateEnd),200,""));
//    }
//
//    @GetMapping("countSuccessOrder")
//    public ResponseEntity<?> countSuccessOrder(@RequestParam("start_date") String startDate,
//                                           @RequestParam("end_date")String endDate){
//        startDate=startDate.replace(" ","T");
//        endDate=endDate.replace(" ","T");
//        LocalDateTime dateStart= LocalDateTime.parse(startDate);
//        LocalDateTime dateEnd=LocalDateTime.parse(endDate);
//        return ResponseEntity.ok().body(new IGenericResponse<>(orderService.countsuccessOrderByTime(dateStart,dateEnd),200,""));
//    }
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteOrder(@RequestParam("order_id") Integer orderId) {
        Optional<Order> optionalOrder = orderService.findById(orderId);
        if (optionalOrder.isPresent()) {
            detailOrderService.deleteAllByOrderId(orderId);
            orderService.deleteById(orderId);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found Order"));

    }
    @GetMapping("orderSatistic")
    public ResponseEntity<?> sumTotalPriceByTime(){
        List<Dashboard> dashboardList= new ArrayList<>();
        Dashboard dashboard= new Dashboard();
        dashboard.setName("Tổng đơn hàng");
         Double thang1=orderService.sumTotalPriceByTime(1);
        Double thang2=orderService.sumTotalPriceByTime(2);
        Double thang3=orderService.sumTotalPriceByTime(3);
        Double thang4=orderService.sumTotalPriceByTime(4);
        Double thang5=orderService.sumTotalPriceByTime(5);
        Double thang6=orderService.sumTotalPriceByTime(6);
        List<Double> doubleList= new ArrayList<>();
        doubleList.add(thang1);
        doubleList.add(thang2);
        doubleList.add(thang3);
        doubleList.add(thang4);
        doubleList.add(thang5);
        doubleList.add(thang6);
        dashboard.setData(doubleList);
        List<String> thang= new ArrayList<>();

        thang.add("January");
        thang.add("February");
        thang.add("March");
        thang.add("April");
        thang.add("May");
        thang.add("June");
        thang.add("July");
        dashboard.setLabels(thang);
        Dashboard dashboard1= new Dashboard();
        dashboard1.setName("Đơn thành công");


        List<Double> doubleList1= Arrays.asList(orderService.sumSuccessOrderByTime(1),
                orderService.sumSuccessOrderByTime(2),
                orderService.sumSuccessOrderByTime(3),
                orderService.sumSuccessOrderByTime(4),
                orderService.sumSuccessOrderByTime(5),
                orderService.sumSuccessOrderByTime(6));
        dashboard1.setData(doubleList1);
        dashboard1.setLabels(thang);
        Dashboard dashboard2= new Dashboard();
        dashboard2.setName("Đơn Hủy");

        List<Double> doubleList2=Arrays.asList(orderService.sumCancelOrderByTime(1),
        orderService.sumCancelOrderByTime(2),
        orderService.sumCancelOrderByTime(3),
        orderService.sumCancelOrderByTime(4),
        orderService.sumCancelOrderByTime(5),
        orderService.sumCancelOrderByTime(6));
        dashboard2.setData(doubleList2);

        dashboard2.setLabels(thang);
        Dashboard dashboard3= new Dashboard();
        dashboard3.setName("Đơn Đổi Trả");

        List<Double> doubleList3=Arrays.asList(orderService.sumPaybackOrderByTime(1),
        orderService.sumPaybackOrderByTime(2),
        orderService.sumPaybackOrderByTime(3),
        orderService.sumPaybackOrderByTime(4),
        orderService.sumPaybackOrderByTime(5),
        orderService.sumPaybackOrderByTime(6));
        dashboard3.setData(doubleList3);

        dashboard3.setLabels(thang);
        dashboardList.add(dashboard);
        dashboardList.add(dashboard1);
        dashboardList.add(dashboard2);
        dashboardList.add(dashboard3);
        return ResponseEntity.ok().body(new IGenericResponse<>(dashboardList,200,""));
    }


}
