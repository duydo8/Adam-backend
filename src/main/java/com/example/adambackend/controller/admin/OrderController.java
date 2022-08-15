package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.*;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.order.*;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.*;
import com.example.adambackend.service.*;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    DiscountOrderRepository discountOrderRepository;
    @Autowired
    AddressService addressService;

    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam(value = "status", required = false)
                                                  Integer status,
                                              @RequestParam("page") Integer page,
                                              @RequestParam("size") Integer size)
                                               {
        try {

            Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").ascending());
            Integer totalElement=orderService.countTotalElementOrder(status);
            List<OrderFindAll> orderFindAlls= orderService.findByStatus(pageable,status);
            List<OrderFindAllResponse> orderFindAllResponses=orderFindAlls.stream()
                    .map(e->new OrderFindAllResponse(e.getId(),e.getStatus(),e.getCreateDate(),
                            accountService.findByIds(e.getAccountId()),e.getFullName(),e.getPhoneNumber(),e.getAmountPrice(),
                            e.getSalePrice(),e.getTotalPrice(),addressRepository.
                            findByAddressId(e.getAddressId()),e.getAddressDetail(),e.getOrderCode(),totalElement))
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(new IGenericResponse<>(orderFindAllResponses, 200, "Page product"));
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
                if (status == 2) {
                    for (DetailOrder detailOrder : detailOrderList
                    ) {
                        DetailProduct detailProduct = detailOrder.getDetailProduct();
                        detailProduct.setQuantity(detailProduct.getQuantity() - detailOrder.getQuantity());
                        detailProductService.save(detailProduct);
                    }
                }
                if (status == 0) {
                    List<HistoryOrder> historyOrders = historyOrderRepository.findByOrderId(orderId);
                    for (HistoryOrder ho : historyOrders
                    ) {
                        if (ho.getStatus() == 2) {
                            for (DetailOrder detailOrder : detailOrderList
                            ) {
                                DetailProduct detailProduct = detailOrder.getDetailProduct();
                                detailProduct.setQuantity(detailProduct.getQuantity() - detailOrder.getQuantity());
                                detailProductService.save(detailProduct);
                            }
                        }
                    }
                    Account account = accountService.findById(orderOptional.get().getAccount().getId()).get();
                    if (account.getPriority() == -5) {

                    } else {
                        double priority = account.getPriority() - orderOptional.get().getTotalPrice() * 0.0000001;
                        if (priority < -5) {
                            account.setPriority(5.0);
                        }
                        account.setPriority(priority);
                    }
                }
                if (status == 6) {
                    Account account = accountService.findById(orderOptional.get().getAccount().getId()).get();
                    if (account.getPriority() == 5) {

                    } else {
                        double priority = account.getPriority() + orderOptional.get().getTotalPrice() * 0.0000001;
                        if (priority > 5) {
                            account.setPriority(5.0);
                        }
                        account.setPriority(priority);
                    }
                }


                HistoryOrder historyOrder = new HistoryOrder();


                historyOrder.setOrder(orderOptional.get());
                historyOrder.setDescription("update time");
                historyOrder.setUpdateTime(LocalDateTime.now());
                historyOrder.setIsActive(true);
                historyOrder.setTotalPrice(orderOptional.get().getTotalPrice());
                historyOrder.setStatus(6);
                historyOrder = historyOrderRepository.save(historyOrder);
                List<HistoryOrder> historyOrders = orderOptional.get().getHistoryOrders();
                historyOrders.add(historyOrder);
                orderOptional.get().setHistoryOrders(historyOrders);

                return ResponseEntity.ok().body(new IGenericResponse<Order>(orderService.save(orderOptional.get()), 200, ""));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy event"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
//    @PostMapping("updateReturnOrder")
//    public ResponseEntity<?> updateReturnOrder(@RequestBody OrderReturn orderReturn){
//        Order
//    }

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

    @PostMapping("create")
    public ResponseEntity<?> createOrder(@RequestBody OrderWebsiteCreate orderWebsiteCreate) {
        try {
            Optional<Account> account = accountService.findById(orderWebsiteCreate.getAccountId());
            Optional<Address> address = addressService.findById(orderWebsiteCreate.getAddressId());
            if (address.isPresent() && account.isPresent()) {
                Order order = new Order();
                order.setAccount(account.get());
                order.setCreateDate(LocalDateTime.now());
                order.setStatus(1);
                order.setAddress(address.get());
                order.setFullName(orderWebsiteCreate.getFullName());
                order.setPhoneNumber(orderWebsiteCreate.getPhoneNumber());

                Double ammountPrice = 0.0;
                order.setAmountPrice(ammountPrice);
                order.setAddressDetail(orderWebsiteCreate.getAddressDetail());
                order.setTotalPrice(0.0);
                order.setSalePrice(0.0);
                order = orderService.save(order);
                List<CartItems> cartItemsList = new ArrayList<>();


//
                for (Integer x : orderWebsiteCreate.getCartItemIdList()
                ) {
                    Optional<CartItems> cartItemsOptional = cartItemService.findById(x);
                    if (cartItemsOptional.isPresent()) {

                        cartItemsList.add(cartItemsOptional.get());
                        DetailProduct detailProduct = cartItemsOptional.get().getDetailProduct();

                        if (detailProduct.getQuantity() - cartItemsOptional.get().getQuantity() < 0) {
                            return ResponseEntity.badRequest().
                                    body(new HandleExceptionDemo(400, "Không đủ số lượng "));
                        }

                        ammountPrice += cartItemsOptional.get().getTotalPrice();
                        detailProduct.setQuantity(detailProduct.getQuantity() - cartItemsOptional.get().getQuantity());
                        detailProductService.save(detailProduct);
                        cartItemService.updateIsActive(x);
                        DetailOrder detailOrder = new DetailOrder();
                        detailOrder.setQuantity(cartItemsOptional.get().getQuantity());
                        detailOrder.setTotalPrice(cartItemsOptional.get().getTotalPrice());
                        detailOrder.setCreateDate(LocalDateTime.now());
                        detailOrder.setPrice(detailProduct.getPriceExport());
                        detailOrder.setIsDeleted(false);
                        detailOrder.setIsActive(true);
                        detailOrder.setDetailProduct(detailProduct);
                        detailOrder.setOrder(order);
                        detailOrderService.save(detailOrder);


                    }
                }

                order.setAmountPrice(ammountPrice);
                order.setCartItems(cartItemsList);
                Double totalPrice = 0.0;
                if (totalPrice > 5000000) {
                    return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,
                            "đơn hàng không được quá 5tr, vui lòng liên hệ admin hoặc đến cửa hàng gần nhất "));
                }
//                List<Order> orders = orderService.findAll();
                String code = RandomString.make(64);
//                for (int i = 0; i < orders.size(); i++) {
//                    if (code.equals(orders.get(i).getOrderCode())) {
//                        code = RandomString.make((64));
//                        break;
//                    }
//                }
                List<Integer> idx= new ArrayList<>();
                List<DiscountOrder> discountOrders= new ArrayList<>();
                List<Event> events= eventRepository.findAllByTime();
                for (Event e: events
                ) {
                    discountOrders= discountOrderRepository.findByTotalPriceAndTime(ammountPrice,e.getId());
                    for (DiscountOrder d: discountOrders
                    ) {
                        idx.add(d.getId());
                    }

                }
                System.out.println(idx);
                Double salePrice=0.0;
                Double salePricePercent=0.0;
                for (Integer x: idx
                ) {
                    DiscountOrder discountOrder=discountOrderRepository.getById(x);

                    if(discountOrder.getSalePrice()<1){
                        salePricePercent+= discountOrder.getSalePrice();

                    }else {
                        salePrice += discountOrder.getSalePrice();
                    }
                }
                double totalSalePrice=salePrice + (salePricePercent*ammountPrice);
                order.setSalePrice((double)Math.round(totalSalePrice));
                totalPrice=ammountPrice-totalSalePrice;
                order.setOrderCode(code);
                HistoryOrder historyOrder = new HistoryOrder();
                order.setTotalPrice(totalPrice);
                order = orderService.save(order);

                historyOrder.setOrder(orderService.findById(order.getId()).get());
                historyOrder.setDescription("create time");
                historyOrder.setUpdateTime(LocalDateTime.now());
                historyOrder.setIsActive(true);
                historyOrder.setTotalPrice(order.getTotalPrice());
                historyOrder.setStatus(1);
                historyOrder = historyOrderRepository.save(historyOrder);
                List<HistoryOrder> historyOrders = new ArrayList<>();
                historyOrders.add(historyOrder);
                order.setHistoryOrders(historyOrders);
                order = orderService.save(order);
                for (Integer x : orderWebsiteCreate.getCartItemIdList()
                ) {
                    Optional<CartItems> cartItemsOptional = cartItemService.findById(x);
                    if (cartItemsOptional.isPresent()) {
                        cartItemsOptional.get().setOrder(order);
                        cartItemService.save(cartItemsOptional.get());
                    }
                }

                return ResponseEntity.ok().body(new IGenericResponse<>(order, 200, ""));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy "));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
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

    @GetMapping("findById")
    public ResponseEntity<?> findById(@RequestParam("id") Integer id) {
        try {
            Optional<Order> order = orderService.findById(id);
            if (order.isPresent()) {
                return ResponseEntity.ok(new IGenericResponse<>(order.get(), 200, ""));

            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("findByAccountId")
    public ResponseEntity<?> findByAccountId(@RequestParam("account_id") Integer accountId, @RequestParam("status") Integer status) {
        try {
            Optional<Account> account = accountService.findById(accountId);
            if (account.isPresent()) {
                return ResponseEntity.ok().body(new IGenericResponse<>(orderService.findOrderByAccountId(accountId, status), 200, ""));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }


}
