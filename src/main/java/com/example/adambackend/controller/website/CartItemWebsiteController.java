package com.example.adambackend.controller.website;

import com.example.adambackend.entities.CartItems;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*",maxAge = 3600 )
@RequestMapping("cart")
public class CartItemWebsiteController {
    @Autowired
    CartItemService cartItemService;

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody CartItems cartItems) {

        return ResponseEntity.ok().body(new IGenericResponse<CartItems>(cartItemService.save(cartItems), 200, "success"));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody CartItems cartItems) {
        Optional<CartItems> cartItemsOptional = cartItemService.findById(cartItems.getId());
        if (cartItemsOptional.isPresent()) {
            return ResponseEntity.ok().body(new IGenericResponse<CartItems>(cartItemService.save(cartItems), 200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("detail_order_id") Integer id) {
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
}
