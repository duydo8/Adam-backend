package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Tag;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.ProductSevice;
import com.example.adambackend.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/product")
public class ProductWebsiteController {
    @Autowired
    ProductSevice productSevice;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TagService tagService;

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
    @GetMapping("findProductByTagName")
    public ResponseEntity<?> findProductByTag(@RequestParam("tag_name") String tagName){
        Optional<Tag> tagOptional= tagService.findByTagName(tagName);
        if(tagOptional.isPresent()){
            return ResponseEntity.ok().body(new IGenericResponse<List<Product>>(productSevice.findAllByTagName(tagName),200,""));
        }else{
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found"));
        }
    }

}
