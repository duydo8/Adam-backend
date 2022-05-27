package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductWebsiteController {
    @Autowired
    ProductSevice productSevice;
    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam("page")int page,@RequestParam("size")int size){
       Page<Product>page1= productSevice.findPage(page,size);
        return  ResponseEntity.ok().body(new IGenericResponse<Page<Product>>(page1,200,"Page product"));
    }

}
