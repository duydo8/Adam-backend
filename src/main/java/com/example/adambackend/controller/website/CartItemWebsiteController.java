package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.CartItems;
import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.cart.CartItemWebsiteCreate;
import com.example.adambackend.payload.cart.CartItemWebsiteUpdate;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.CartItemService;
import com.example.adambackend.service.DetailProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/user/cart")
public class CartItemWebsiteController {
    @Autowired
    CartItemService cartItemService;
    @Autowired
    AccountService accountService;
    @Autowired
    DetailProductService detailProductService;

//    private Integer id;
//    private int quantity;
//    @Column(name = "total_price")
//    private double totalPrice;
//    @ManyToOne
//    @JoinColumn(name = "account_id")
//    private Account account;
//    @ManyToOne
//    @JoinColumn(name = "detail_product_id")
//    private DetailProduct detailProduct;
//    @Column(name = "is_active")
//    private Boolean isActive;
//    @Column(name = "create_date")
//    private LocalDateTime createDate;
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody CartItemWebsiteCreate cartItemWebsiteCreate) {
CartItems cartItems= new CartItems(null, cartItemWebsiteCreate.getQuantity()
,cartItemWebsiteCreate.getTotalPrice(),accountService.findById(cartItemWebsiteCreate.getAccountId()).get(),
        detailProductService.findById(cartItemWebsiteCreate.getDetailProductId()).get(),
        true, LocalDateTime.now());
        return ResponseEntity.ok().body(new IGenericResponse<CartItems>(cartItemService.save(cartItems), 200, "success"));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody CartItemWebsiteUpdate cartItemWebsiteUpdate) {
        Optional<CartItems> cartItemsOptional = cartItemService.findById(cartItemWebsiteUpdate.getId());
        if (cartItemsOptional.isPresent()) {
         CartItems cartItems= cartItemsOptional.get();
         cartItems.setQuantity(cartItemWebsiteUpdate.getQuantity());
         cartItems.setTotalPrice(cartItemWebsiteUpdate.getTotalPrice());
            return ResponseEntity.ok().body(new IGenericResponse<CartItems>(cartItemService.save(cartItems), 200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        Optional<CartItems> cartItemsOptional = cartItemService.findById(id);
        if (cartItemsOptional.isPresent()) {
            cartItemService.deleteById(id);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }

    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(new IGenericResponse<List<CartItems>>(cartItemService.findAll(), 200, ""));
    }
    @GetMapping("findById")
    public ResponseEntity<?> findById(@RequestParam("id")Integer id){
        Optional<CartItems> cartItems= cartItemService.findById(id);
        if(cartItems.isPresent()){
            return ResponseEntity.ok(new IGenericResponse<>(cartItems.get(), 200, ""));

        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));

    }
}
