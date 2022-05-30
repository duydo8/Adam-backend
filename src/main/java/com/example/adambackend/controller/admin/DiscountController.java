package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Discount;
import com.example.adambackend.entities.Event;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.DiscountService;
import com.example.adambackend.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin/discount")
public class DiscountController {
    @Autowired
    DiscountService discountService;
    @Autowired
    EventService eventService;
    @PostMapping("create")
    public ResponseEntity<?> createDiscount(@RequestBody Discount discount, @RequestParam("event_id")Long eventId){
        Optional<Event> eventOptional= eventService.findById(eventId);
        if(eventOptional.isPresent()){
            List<Discount> discounts= eventOptional.get().getDiscounts();
            discounts.add(discount);
            eventOptional.get().setDiscounts(discounts);
            eventService.save(eventOptional.get());
            return ResponseEntity.ok().body(new IGenericResponse<Discount>(discountService.save(discount),200,""));
        }
        else{
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found Event"));
        }
    }
    @PutMapping("update")
    public ResponseEntity<?> updateDiscount(@RequestBody Discount discount, @RequestParam("event_id")Long eventId){
        Optional<Event> eventOptional= eventService.findById(eventId);
        Optional<Discount> discount1=discountService.findById(discount.getId());
        if(eventOptional.isPresent() & discount1.isPresent()){
            List<Discount> discounts= eventOptional.get().getDiscounts();
            discounts.add(discount);
            eventOptional.get().setDiscounts(discounts);
            eventService.save(eventOptional.get());
            return ResponseEntity.ok().body(new IGenericResponse<Discount>(discountService.save(discount),200,""));
        }
        else{
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found Event"));
        }
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteDiscount( @RequestParam("event_id")Long eventId,@RequestParam("discount_id")Long id){
        Optional<Event> eventOptional= eventService.findById(eventId);
        Optional<Discount> discount1=discountService.findById(id);
        if(eventOptional.isPresent() & discount1.isPresent()){
            List<Discount>discounts=eventOptional.get().getDiscounts();
            discounts.remove(discount1.get());
            eventOptional.get().setDiscounts(discounts);
            eventService.save(eventOptional.get());
            discountService.deleteById(id);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200,"successfully"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found"));
    }


}
