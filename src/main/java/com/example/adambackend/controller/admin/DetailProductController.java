package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Size;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.detailProduct.CustomDetailProductResponse;
import com.example.adambackend.payload.detailProduct.DetailProductDTO;
import com.example.adambackend.payload.detailProduct.ListDetailProductIdDTO;
import com.example.adambackend.payload.detailProduct.NewDetailProductDTO;
import com.example.adambackend.payload.detailProduct.DetailProductUpdateAdmin;
import com.example.adambackend.payload.request.DetailProductRequest;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.DetailProductRepository;
import com.example.adambackend.service.ColorService;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.ProductSevice;
import com.example.adambackend.service.SizeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequestMapping("admin/detailProduct")
public class DetailProductController {
    @Autowired
    DetailProductRepository detailProductService;
    @Autowired
    ProductSevice productSevice;
    @Autowired
    ColorService colorService;
    @Autowired
    SizeService sizeService;
    @Autowired
    ModelMapper modelMapper;

    @PostMapping("create")
    public ResponseEntity<?> createDetailProduct(@RequestBody DetailProductDTO detailProductDTO) {
        Optional<Product> product = productSevice.findById(detailProductDTO.getProductId());
        Optional<Color> color = colorService.findById(detailProductDTO.getColorId());
        Optional<Size> size = sizeService.findById(detailProductDTO.getSizeId());
        if (product.isPresent() && colorService.findById(detailProductDTO.getColorId()).isPresent()
                && sizeService.findById(detailProductDTO.getSizeId()).isPresent()) {

            DetailProduct detailProduct = new DetailProduct();
            detailProduct.setQuantity(detailProductDTO.getQuantity());
            detailProduct.setProduct(product.get());
            detailProduct.setProductImage(detailProductDTO.getProductImage());
            detailProduct.setPriceImport(detailProductDTO.getPriceImport());
            detailProduct.setPriceExport(detailProductDTO.getPriceExport());
            detailProduct.setIsDelete(false);
            detailProduct.setColor(color.get());
            detailProduct.setSize(size.get());
            detailProduct.setIsActive(true);
            detailProduct.setCreateDate(LocalDateTime.now());
            detailProductService.save(detailProduct);
            List<DetailProduct> detailProducts = product.get().getDetailProducts();
            detailProducts.add(detailProduct);
            product.get().setDetailProducts(detailProducts);
            productSevice.save(product.get());
            detailProduct.setProduct(product.get());
            return ResponseEntity.ok().body(new IGenericResponse<DetailProduct>(detailProductService.save(detailProduct), 200, "success"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found "));

    }

    @PutMapping("update")
    public ResponseEntity<?> updateDetailProduct( @RequestBody DetailProductUpdateAdmin detailProductUpdateAdmin) {

        Optional<DetailProduct> detailProductOptional= detailProductService.findById(detailProductUpdateAdmin.getId());
        Optional<Size> sizeOptional= sizeService.findById(detailProductUpdateAdmin.getSizeId());
        Optional<Color>colorOptional= colorService.findById(detailProductUpdateAdmin.getColorId());
        if(detailProductOptional.isPresent()&& colorOptional.isPresent()&&sizeOptional.isPresent())
        {

            DetailProduct detailProduct=detailProductOptional.get();
            detailProduct.setQuantity(detailProductUpdateAdmin.getQuantity());
            detailProduct.setPriceExport(detailProductUpdateAdmin.getPriceExport());
            detailProduct.setPriceImport(detailProductUpdateAdmin.getPriceImport());
            detailProduct.setIsDelete(detailProductUpdateAdmin.getIsDelete());
            detailProduct.setProductImage(detailProductUpdateAdmin.getProductImage());
            detailProduct.setIsActive(detailProductUpdateAdmin.getIsActive());
            detailProduct.setColor(colorOptional.get());
            detailProduct.setSize(sizeOptional.get());
            return ResponseEntity.ok().body(new IGenericResponse<>(detailProductUpdateAdmin,200,""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));

    }

    @DeleteMapping("delete")
    public ResponseEntity<?> deleteDetailProduct(@RequestParam("product_id") Integer productId, @RequestParam("detail_product_id") Integer detailProductId) {
        Optional<Product> product = productSevice.findById(productId);
        Optional<DetailProduct> detailProduct = detailProductService.findById(detailProductId);
        if (product.isPresent() && detailProduct.isPresent()) {
            List<DetailProduct> detailProducts = product.get().getDetailProducts();
            for (DetailProduct detailProduct1 : detailProducts
            ) {
                if (detailProduct1.equals(detailProduct.get())) {
                    detailProducts.remove(detailProduct1);
                    product.get().setDetailProducts(detailProducts);
                    productSevice.save(product.get());
                    return ResponseEntity.badRequest().body(new IGenericResponse<>(productSevice.save(product.get()), 200, "success"));
                }
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
    }

    @PostMapping("createArrayOptionValueDetailProduct")
    public ResponseEntity<?> createArrayOptionValueDetailProduct(@RequestBody DetailProductRequest detailProductRequest) {
        Optional<Product> productOptional = productSevice.findById(detailProductRequest.getProductId());
        List<Color> colorList = new ArrayList<>();
        List<Size> sizeList = new ArrayList<>();
        for (Integer colorId : detailProductRequest.getColorIdList()
        ) {

            Optional<Color> color = colorService.findById(colorId);
            if (color.isPresent()) {
                colorList.add(color.get());
            }
        }
        for (Integer s : detailProductRequest.getSizeIdList()
        ) {
            Optional<Size> size = sizeService.findById(s);
            if (size.isPresent()) {
                sizeList.add(size.get());
            }

        }


        List<DetailProduct> detailProductList = new ArrayList<>();
        if (productOptional.isPresent()) {
            for (int i = 0; i < sizeList.size(); i++) {
                for (int j = 0; j < colorList.size(); j++) {
                    DetailProduct detailProduct = new DetailProduct();
                    detailProduct.setProduct(productSevice.findById(detailProductRequest.getProductId()).get());
                    detailProduct.setPriceImport(detailProductRequest.getPriceImport());
                    detailProduct.setPriceExport(detailProductRequest.getPriceExport());
                    detailProduct.setQuantity(detailProductRequest.getQuantity());
                    detailProduct.setIsDelete(false);
                    detailProduct.setIsActive(true);

                    detailProduct.setCreateDate(LocalDateTime.now());
                    detailProduct.setColor(colorList.get(j));
                    detailProduct.setSize(sizeList.get(i));
                    detailProductList.add(detailProduct);
                    detailProductService.save(detailProduct);

                }

            }
            productOptional.get().setIsComplete(false);
            return ResponseEntity.ok().body(new IGenericResponse<List<DetailProduct>>(detailProductList, 200, ""));

        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not exists"));
        }

    }

    @PutMapping("updateListDetailProductAfterCreate")
    public ResponseEntity<?> updateListDetailProductAfterCreate(@RequestBody CustomDetailProductResponse customDetailProductResponse) {
        List<NewDetailProductDTO> newDetailProductDTOList = customDetailProductResponse.getNewDetailProductDTOList();

        List<DetailProduct>detailProducts=new ArrayList<>();
        if (newDetailProductDTOList.size() > 0) {
            for (NewDetailProductDTO n : newDetailProductDTOList
            ) {
                Optional<DetailProduct> detailProduct = detailProductService.findById(n.getId());
                if (detailProduct.isPresent()) {
                    detailProduct.get().setIsActive(n.getIsActive());
                    detailProduct.get().setPriceImport(n.getPriceImport());
                    detailProduct.get().setPriceExport(n.getPriceExport());
                    detailProduct.get().setQuantity(n.getQuantity());

                    detailProduct.get().setProductImage(n.getImage());

                    DetailProduct detailProduct1 = detailProductService.save(detailProduct.get());
                    Product p = productSevice.findByDetailProductId(detailProduct.get().getId());
                    p.setIsComplete(true);
                    productSevice.save(p);
                    detailProducts.add(detailProduct1);

                }

            }
            List<NewDetailProductDTO>  newDetailProductDTOList1=detailProducts.stream().map(e->new NewDetailProductDTO(e.getId(),e.getPriceImport(),
                    e.getPriceExport(),e.getProductImage(),e.getQuantity(),e.getIsActive())).collect(Collectors.toList());
            return ResponseEntity.ok().body(new IGenericResponse<>(detailProducts, 200, ""));


        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "nothing updated"));
    }

    @DeleteMapping("deleteByListId")
    public ResponseEntity<?> deleteArrayTagId(@RequestBody ListDetailProductIdDTO listDetailProductIdDTO) {
        List<Integer> list = listDetailProductIdDTO.getListDetailProductId();


        if (list.size() > 0) {
            for (Integer x : list
            ) {
                Optional<DetailProduct> detailProductOptional = detailProductService.findById(x);

                if (detailProductOptional.isPresent()) {
                    detailProductService.updateDetailProductsDeleted(x);

                }
            }
            return ResponseEntity.ok().body(new IGenericResponse<>("", 200, ""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found "));
    }

}
