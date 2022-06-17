package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Size;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.payload.response.ProductDto;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController

@RequestMapping("admin/product")
public class ProductController {
    @Autowired
    ProductSevice productSevice;

    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Product> page1 = productSevice.findPage(page, size);
        Page<ProductDto> pageDtos = (Page<ProductDto>) page1.stream().map(e -> new ProductDto(e.getId(), e.getProductName(), e.getDescription(), e.isDelete(), e.getImage())).collect(Collectors.toList());
        return ResponseEntity.ok().body(new IGenericResponse<Page<ProductDto>>(pageDtos, 200, "Page product"));
    }

    @PostMapping("create")
    public ResponseEntity<IGenericResponse<Product>> create(@RequestBody Product product){

        return ResponseEntity.ok().body(new IGenericResponse<Product>(productSevice.save(product),200,"success"));
    }
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody Product product){
        Optional<Product> product1= productSevice.findById(product.getId());
        if(product1.isPresent()){
            return ResponseEntity.ok().body(new IGenericResponse<Product>(productSevice.save(product),200,"success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found"));
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("product_id")Integer sizeId){
        Optional<Product> product1= productSevice.findById(sizeId);
        if(product1.isPresent()){
            productSevice.deleteById(sizeId);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200,"success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found"));
    }

}
