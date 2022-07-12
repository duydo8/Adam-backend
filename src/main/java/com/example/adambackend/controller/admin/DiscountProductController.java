package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.DiscountProduct;
import com.example.adambackend.entities.Event;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.DiscountProductRepository;
import com.example.adambackend.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/discountProduct")
public class DiscountProductController {
    @Autowired
    DiscountProductRepository discountProductRepository;
    @Autowired
    EventRepository eventRepository;
    @GetMapping("create")
    public ResponseEntity<?> create(@RequestBody DiscountProduct discountProduct){
        return ResponseEntity.ok(new IGenericResponse<>(discountProductRepository.save(discountProduct),200,"")) ;
    }
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
}
