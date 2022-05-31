package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.payload.response.ProductDto;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}
