package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.*;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.ProductDTO;
import com.example.adambackend.payload.request.ProductRequest;
import com.example.adambackend.payload.response.AccountDto;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.payload.response.ProductDto;
import com.example.adambackend.repository.MaterialProductRepository;
import com.example.adambackend.repository.TagProductRepository;
import com.example.adambackend.service.CategoryService;
import com.example.adambackend.service.MaterialService;
import com.example.adambackend.service.ProductSevice;
import com.example.adambackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("admin/product")
public class ProductController {
    @Autowired
    ProductSevice productSevice;
    @Autowired
    MaterialService materialService;
    @Autowired
    TagService tagService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    MaterialProductRepository materialProductRepository;
    @Autowired
    TagProductRepository tagProductRepository;

    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Product> page1 = productSevice.findPage(page, size);
        return ResponseEntity.ok().body(new IGenericResponse<Page<Product>>(page1, 200, "Page product"));
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody ProductDTO productDTO) {
        if(categoryService.findById(productDTO.getCategoryId()).isPresent()){
            Product product= new Product();
            product.setVoteAverage(0);
            product.setDelete(false);
            product.setProductName(productDTO.getProductName());
            product.setDescription(productDTO.getDescription());
            product.setImage(productDTO.getImage());
            product.setCreateDate(LocalDateTime.now());
            product.setCategory(categoryService.findById(productDTO.getCategoryId()).get());
            return ResponseEntity.ok().body(new IGenericResponse<Product>(productSevice.save(product), 200, "success"));
        }else{
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
        }



    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody Product product) {
        Optional<Product> product1 = productSevice.findById(product.getId());
        if (product1.isPresent()) {
            return ResponseEntity.ok().body(new IGenericResponse<Product>(productSevice.save(product), 200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("product_id") Integer sizeId) {
        Optional<Product> product1 = productSevice.findById(sizeId);
        if (product1.isPresent()) {
            productSevice.deleteById(sizeId);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }

    @PostMapping("createArrayOptionValueProduct")
    public ResponseEntity<?> createArrayOptionValueProduct(@RequestBody ProductRequest productRequest) {
        List<Tag> tagList = new ArrayList<>();
        List<Material> materialList = new ArrayList<>();
        for (Integer materialId : productRequest.getMaterialProductIdList()
        ) {

            Optional<Material> materialOptional = materialService.findById(materialId);
            if (materialOptional.isPresent()) {
                materialList.add(materialOptional.get());
            }
        }
        for (Integer s : productRequest.getTagProductIdList()
        ) {
            Optional<Tag> tagOptional = tagService.findById(s);
            if (tagOptional.isPresent()) {
                tagList.add(tagOptional.get());
            }

        }


        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setVoteAverage(0);
        product.setDelete(false);
        product.setProductName(productRequest.getProductName());
        product.setDescription(product.getDescription());
        product.setImage(productRequest.getImage());
        product.setCreateDate(LocalDateTime.now());
        product.setCategory(categoryService.findById(productRequest.getCategoryId()).get());
        productSevice.save(product);
        List<TagProduct> tagProductList = new ArrayList<>();
        List<MaterialProduct> materialProductList = new ArrayList<>();
        if (categoryService.findById(productRequest.getCategoryId()).isPresent()) {
            for (int i = 0; i < tagList.size(); i++) {
                for (int j = 0; j < materialList.size(); j++) {
                    Optional<Material> material = materialService.findById(j);
                    Optional<Tag> tagOptional = tagService.findById(i);
                    if (material.isPresent() && tagOptional.isPresent()) {
                        MaterialProduct materialProduct = new MaterialProduct
                                (new MaterialProductPK(j, product.getId()),
                                        false, material.get(),false,LocalDateTime.now(), product);
                        TagProduct tagProduct = new TagProduct(new TagProductPK(i, product.getId()), false, tagOptional.get(),false, product,LocalDateTime.now());
                        materialProductRepository.save(materialProduct);
                        tagProductRepository.save(tagProduct);
                        materialProductList.add(materialProduct);
                        tagProductList.add(tagProduct);
                    }
                }


            }
            product.setTagProducts(tagProductList);
            product.setMaterialProducts(materialProductList);

            return ResponseEntity.ok().body(new IGenericResponse<>(productSevice.save(product), 200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not exists"));

        }


    }
}
