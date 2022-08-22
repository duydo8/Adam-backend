package com.example.adambackend.controller.admin;

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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/admin/cart")
public class CartItemController {
    @Autowired
    CartItemService cartItemService;
    @Autowired
    AccountService accountService;
    @Autowired
    DetailProductService detailProductService;


    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody CartItemWebsiteCreate cartItemWebsiteCreate) {
        try {
            Optional<Account> accountOptional = accountService.findById(cartItemWebsiteCreate.getAccountId());
            Optional<DetailProduct> detailProductOptional = detailProductService.findById(cartItemWebsiteCreate.getDetailProductId());
            if (cartItemWebsiteCreate.getQuantity() >= 10) {
                return ResponseEntity.badRequest().body(
                        new HandleExceptionDemo(400, "Không thể mua số lượng >10"));
            }
            if (detailProductOptional.get().getQuantity() < cartItemWebsiteCreate.getQuantity()) {
                return ResponseEntity.badRequest().body(
                        new HandleExceptionDemo(400, "Không đủ số lượng"));
            }
            if (accountOptional.isPresent() && detailProductOptional.isPresent()) {
                List<CartItems> cartItemsList = cartItemService.findByAccountId(cartItemWebsiteCreate.getAccountId());
                for (CartItems c : cartItemsList
                ) {
                    if (detailProductOptional.get().getId() == c.getDetailProduct().getId()) {
                        c.setQuantity(c.getQuantity() + 1);
                        return ResponseEntity.ok().body(new IGenericResponse<CartItems>(cartItemService.save(c), 200, "success"));
                    }
                }

                CartItems cartItems = new CartItems(null, cartItemWebsiteCreate.getQuantity()
                        , cartItemWebsiteCreate.getQuantity() * detailProductOptional.get().getPriceExport(), accountService.findById(cartItemWebsiteCreate.getAccountId()).get(),
                        detailProductOptional.get(),
                        true, LocalDateTime.now(), null);
                return ResponseEntity.ok().body(new IGenericResponse<CartItems>(cartItemService.save(cartItems), 200, "success"));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody CartItemWebsiteUpdate cartItemWebsiteUpdate) {
        try {
            Optional<CartItems> cartItemsOptional = cartItemService.findById(cartItemWebsiteUpdate.getId());
            if (cartItemsOptional.isPresent()) {
                CartItems cartItems = cartItemsOptional.get();
                cartItems.setQuantity(cartItemWebsiteUpdate.getQuantity());
                cartItems.setTotalPrice(cartItemWebsiteUpdate.getTotalPrice());
                return ResponseEntity.ok().body(new IGenericResponse<CartItems>(cartItemService.save(cartItems), 200, "success"));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("id") Integer id) {
        try {
            Optional<CartItems> cartItemsOptional = cartItemService.findById(id);
            if (cartItemsOptional.isPresent()) {
                cartItemService.deleteById(id);
                return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(new IGenericResponse<List<CartItems>>(cartItemService.findAll(), 200, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("findById")
    public ResponseEntity<?> findById(@RequestParam("id") Integer id) {
        try {
            Optional<CartItems> cartItems = cartItemService.findById(id);
            if (cartItems.isPresent()) {
                return ResponseEntity.ok(new IGenericResponse<>(cartItems.get(), 200, ""));

            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("findByAccountId")
    public ResponseEntity<?> findByAccountId(@RequestParam("account_id") Integer accountId) {
        try {
            Optional<Account> account = accountService.findById(accountId);
            if (account.isPresent()) {
                return ResponseEntity.ok().body(new IGenericResponse<>(cartItemService.findByAccountId(accountId), 200, ""));


            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
}
