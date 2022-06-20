package com.example.adambackend.controller.website;

import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.entities.Product;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.DetailOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("orderDetail")
public class DetailOrderWebsiteController {
    @Autowired
    DetailOrderService detailOrderService;

    @GetMapping("findAllByOrderId")
    public ResponseEntity<?> findAllByOrderId(@RequestParam("order_id") Integer orderId) {
        return ResponseEntity.ok().body(new IGenericResponse<List<DetailOrder>>(detailOrderService.findAllByOrderId(orderId), 200, ""));
    }

    @GetMapping("findTop10ProductByCountQuantityInOrderDetail")
    public ResponseEntity<?> findTop10ProductByCountQuantityInOrderDetail() {
        return ResponseEntity.ok().body(new IGenericResponse<List<Product>>(detailOrderService.findTop10ProductByCountQuantityInOrderDetail(), 200, ""));
    }
    @PostMapping("create")
    public ResponseEntity<?> creatSize(@RequestBody DetailOrder detailOrder){

        return ResponseEntity.ok().body(new IGenericResponse<DetailOrder>(detailOrderService.save(detailOrder),200,"success"));
    }
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody DetailOrder detailOrder){
        Optional<DetailOrder> detailOrder1= detailOrderService.findById(detailOrder.getId());
        if(detailOrder1.isPresent()){
            return ResponseEntity.ok().body(new IGenericResponse<DetailOrder>(detailOrderService.save(detailOrder),200,"success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found"));
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("detail_order_id")Integer id){
        Optional<DetailOrder> detailOrder= detailOrderService.findById(id);
        if(detailOrder.isPresent()){
            detailOrderService.deleteById(id);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200,"success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found"));
    }
    @GetMapping("findAll")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(new IGenericResponse<List<DetailOrder>>(detailOrderService.findAll(),200,""));
    }
}
