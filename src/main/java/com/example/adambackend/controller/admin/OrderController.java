package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.entities.Order;
import com.example.adambackend.enums.OrderStatus;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController

@RequestMapping("admin/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    DetailProductService detailProductService;
    @Autowired
    AccountService accountService;

    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Order> page1 = orderService.findAll(pageable);
        return ResponseEntity.ok().body(new IGenericResponse<Page<Order>>(page1, 200, "Page product"));
    }

    @PutMapping("update")
    public ResponseEntity<?> updateEvent(@RequestBody Order order) {
        Optional<Order> materialOptional = orderService.findById(order.getId());
        if (materialOptional.isPresent()) {

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
            orderOptional.get().setStatus(OrderStatus.success);
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

            return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));

        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found "));
    }
    @GetMapping("countAllOrder")
    public ResponseEntity<?> countAllOrder(@RequestParam("start_date") String startDate,
                                           @RequestParam("end_date")String endDate){
        startDate=startDate.replace(" ","T");
        endDate=endDate.replace(" ","T");
        LocalDateTime dateStart= LocalDateTime.parse(startDate);
        LocalDateTime dateEnd=LocalDateTime.parse(endDate);
        return ResponseEntity.ok().body(new IGenericResponse<>(orderService.countAllOrderByTime(dateStart,dateEnd),200,""));
    }
    @GetMapping("countCancelOrder")
    public ResponseEntity<?> countCancelOrder(@RequestParam("start_date") String startDate,
                                           @RequestParam("end_date")String endDate){
        startDate=startDate.replace(" ","T");
        endDate=endDate.replace(" ","T");
        LocalDateTime dateStart= LocalDateTime.parse(startDate);
        LocalDateTime dateEnd=LocalDateTime.parse(endDate);
        return ResponseEntity.ok().body(new IGenericResponse<>(orderService.countCancelOrderByTime(dateStart,dateEnd),200,""));
    }
    @GetMapping("countSuccessOrder")
    public ResponseEntity<?> countSuccessOrder(@RequestParam("start_date") String startDate,
                                           @RequestParam("end_date")String endDate){
        startDate=startDate.replace(" ","T");
        endDate=endDate.replace(" ","T");
        LocalDateTime dateStart= LocalDateTime.parse(startDate);
        LocalDateTime dateEnd=LocalDateTime.parse(endDate);
        return ResponseEntity.ok().body(new IGenericResponse<>(orderService.countsuccessOrderByTime(dateStart,dateEnd),200,""));
    }

}
