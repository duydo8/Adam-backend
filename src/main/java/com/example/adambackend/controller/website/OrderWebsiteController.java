package com.example.adambackend.controller.website;

import com.example.adambackend.entities.*;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.order.OrderWebsiteCreate;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.DiscountOrderRepository;
import com.example.adambackend.repository.EventRepository;
import com.example.adambackend.repository.HistoryOrderRepository;
import com.example.adambackend.service.*;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 36000000)
@RequestMapping("user/order")
public class OrderWebsiteController {
    @Autowired
    OrderService orderService;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    DiscountOrderRepository discountOrderRepository;
    @Autowired
    AccountService accountService;
    @Autowired
    DetailOrderService detailOrderService;
    @Autowired
    AddressService addressService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    HistoryOrderRepository historyOrderRepository;
    @Autowired
    DetailProductService detailProductService;


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
                        String x1 = RandomString.make(64) + order.getId();
                        detailOrder.setDetailOrderCode(x1);
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

                String code = RandomString.make(64) + order.getId();

                List<Integer> idx = new ArrayList<>();
                List<Event> events = eventRepository.findAllByTime();
                for (Event e : events
                ) {
                    List<DiscountOrder> discountOrders = discountOrderRepository.findByTotalPriceAndTime(ammountPrice, e.getId());
                    for (DiscountOrder d : discountOrders
                    ) {
                        idx.add(d.getId());
                    }

                }
                System.out.println(idx);
                Double salePrice = 0.0;
                Double salePricePercent = 0.0;
                for (Integer x : idx
                ) {
                    DiscountOrder discountOrder = discountOrderRepository.getById(x);

                    if (discountOrder.getSalePrice() < 1) {
                        salePricePercent += discountOrder.getSalePrice();

                    } else {
                        salePrice += discountOrder.getSalePrice();
                    }
                }
                Double totalSalePrice = salePrice + (salePricePercent * ammountPrice);
                order.setSalePrice(totalSalePrice);
                totalPrice = ammountPrice - totalSalePrice;
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
                return ResponseEntity.ok().body(new IGenericResponse<>(orderService.findByAccountId(accountId, status), 200, ""));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }


}
