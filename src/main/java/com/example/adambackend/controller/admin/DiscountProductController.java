package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.DiscountProduct;
import com.example.adambackend.entities.Event;
import com.example.adambackend.entities.Product;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.discountProduct.DiscountProductCreate;
import com.example.adambackend.payload.discountProduct.DiscountProductUpdate;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.DiscountProductRepository;
import com.example.adambackend.repository.EventRepository;
import com.example.adambackend.repository.ProductRepository;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/discountProduct")
public class DiscountProductController {
    @Autowired
    DiscountProductRepository discountProductRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    ProductSevice productSevice;
    @Autowired
    DetailProductService detailProductService;

    @GetMapping("findAll")
    public ResponseEntity<?> findAll(){
           return ResponseEntity.ok(new IGenericResponse<>(discountProductRepository.findAll(),200,""));

    }
    @GetMapping("findById")
    public ResponseEntity<?> findById(@RequestParam("id") Integer id){
        Optional<DiscountProduct> discountProductOptional= discountProductRepository.findById(id);
        if(discountProductOptional.isPresent()) {
            return ResponseEntity.ok(new IGenericResponse<>(discountProductOptional.get(),200,""));

        }
        return ResponseEntity.ok(new HandleExceptionDemo(400,"not found"));
    }
    @GetMapping("findByEventId")
    public ResponseEntity<?> findByEventId(@RequestParam("event_id")Integer eventId){
        Optional<Event> eventOptional= eventRepository.findById(eventId);
        if(eventOptional.isPresent()) {
            return ResponseEntity.ok(new IGenericResponse<>(eventOptional.get(),200,""));

        }
        return ResponseEntity.ok(new HandleExceptionDemo(400,"not found"));
    }
    @GetMapping("create")
    public ResponseEntity<?> create(@RequestBody DiscountProductCreate discountProductCreate){
        DiscountProduct discountProduct= new DiscountProduct();
        Optional<Product>productOptional= productSevice.findById(discountProductCreate.getProductId());
        Optional<Event> eventOptional= eventRepository.findById(discountProductCreate.getEventId());
        if(productOptional.isPresent() && eventOptional.isPresent()){
        discountProduct.setProduct(productOptional.get());
        discountProduct.setEvent(eventOptional.get());
        discountProduct.setDiscountName(discountProductCreate.getDiscountName());
        discountProduct.setCreateDate(LocalDateTime.now());
        discountProduct.setDescription(discountProductCreate.getDescription());
        discountProduct.setIsActive(true);
        discountProduct.setIsDeleted(false);
        discountProduct.setStartTime(discountProductCreate.getStartTime());
        discountProduct.setSalePrice(discountProductCreate.getSalePrice());
        discountProduct.setEndTime(discountProductCreate.getEndTime());

        return ResponseEntity.ok(new IGenericResponse<>(discountProductRepository.save(discountProduct),200,"")) ;
    }
        return ResponseEntity.ok(new HandleExceptionDemo(400,"not found"));
    }
    @PutMapping("update")
    public ResponseEntity<?> update (@RequestBody DiscountProductUpdate discountProductUpdate){

            Optional<DiscountProduct> discountProduct= discountProductRepository.findById(discountProductUpdate.getId());
            Optional<Product>productOptional= productSevice.findById(discountProductUpdate.getProductId());
            Optional<Event> eventOptional= eventRepository.findById(discountProductUpdate.getEventId());
            if(productOptional.isPresent() && eventOptional.isPresent()){
                discountProduct.get().setProduct(productOptional.get());
                discountProduct.get().setEvent(eventOptional.get());
                discountProduct.get().setDiscountName(discountProductUpdate.getDiscountName());

                discountProduct.get().setDescription(discountProductUpdate.getDescription());
                discountProduct.get().setIsActive(discountProductUpdate.getIsActive());
                discountProduct.get().setIsDeleted(discountProductUpdate.getIsDelete());
                discountProduct.get().setStartTime(discountProductUpdate.getStartTime());
                discountProduct.get().setSalePrice(discountProductUpdate.getSalePrice());
                discountProduct.get().setEndTime(discountProductUpdate.getEndTime());

                return ResponseEntity.ok(new IGenericResponse<>(discountProductRepository.save(discountProduct.get()),200,"")) ;
            }
            return ResponseEntity.ok(new HandleExceptionDemo(400,"not found"));

    }
    @PutMapping("updateIsActive")
    public ResponseEntity<?> updateIsActive(@RequestParam("id")Integer id){
        discountProductRepository.updateIsActive(id);
        return ResponseEntity.ok(new IGenericResponse<>("",200,""));
    }

}
