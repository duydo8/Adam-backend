package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.ProductSevice;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductWebsiteController {
    @Autowired
    ProductSevice productSevice;
    @Autowired
    ModelMapper modelMapper;

    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Product> page1 = productSevice.findPage(page, size);
        return ResponseEntity.ok().body(new IGenericResponse<Page<Product>>(page1, 200, "Page product"));
    }

    @GetMapping("findTop10productByCreateDate")
    public ResponseEntity<?> findTop10productByCreateDate() {
        return ResponseEntity.ok().body(new IGenericResponse<List<Product>>(productSevice.findTop10productByCreateDate(), 200, ""));
    }
    @GetMapping("findByColorSizePriceBrandAndMaterial")
    public ResponseEntity<?> findByColorSizePriceBrandAndMaterial(@RequestParam("color_name") String colorName,
                                                              @RequestParam("size_name")String sizeName,
                                                              @RequestParam("brand")String brand,
                                                              @RequestParam("material")String material,
                                                              @RequestParam("bottom_price")double bottomPrice,
                                                              @RequestParam("top_price")double topPrice){
        return ResponseEntity.ok().body(new IGenericResponse<List<Product>>(productSevice.findByColorSizePriceBrandAndMaterial(colorName,
                sizeName, brand, material, bottomPrice, topPrice),200,"success"));

    }

}
