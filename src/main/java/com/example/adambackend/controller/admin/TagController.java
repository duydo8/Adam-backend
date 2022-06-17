package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Category;
import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Tag;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.TagRepository;
import com.example.adambackend.service.ProductSevice;
import com.example.adambackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController

@RequestMapping("admin/tag")
public class TagController {
    @Autowired
    TagService tagService;
    @Autowired
    ProductSevice productSevice;
    @GetMapping("findAll")
    public ResponseEntity<?> findAll(){
        return  ResponseEntity.ok().body(new IGenericResponse<>(tagService.findAll(),200,""));
    }
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody Tag tag,
                                    @RequestParam("product_id") Integer productId){
        Optional<Product> productOptional=productSevice.findById(productId);
        if(productOptional.isPresent()){
            return ResponseEntity.ok().body(new IGenericResponse<>(tagService.save(tag),200,""));
        }
        return  ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found "));
    }
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody Tag tag,
                                    @RequestParam("product_id") Integer productId){
        Optional<Product> productOptional=productSevice.findById(productId);
        Optional<Tag>tagOptional= tagService.findById(tag.getId());
        if(productOptional.isPresent()&& tagOptional.isPresent()){
            return ResponseEntity.ok().body(new IGenericResponse<>(tagService.save(tag),200,""));
        }
        return  ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found "));
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("tag_id") Integer id) {
        Optional<Tag> tagOptional = tagService.findById(id);
        if (tagOptional.isPresent()) {
            tagService.deleteById(id);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found category"));
        }
    }

}
