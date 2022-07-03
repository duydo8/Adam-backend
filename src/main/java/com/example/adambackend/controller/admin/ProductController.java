package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.*;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.ListProductIdDTO;
import com.example.adambackend.payload.ProductDTO;
import com.example.adambackend.payload.ProductResponse;
import com.example.adambackend.payload.ProductUpdateDTO;
import com.example.adambackend.payload.productWebsiteDTO.*;
import com.example.adambackend.payload.request.ProductRequest;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.MaterialProductRepository;
import com.example.adambackend.repository.TagProductRepository;
import com.example.adambackend.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "*", maxAge = 36000)
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
    @Autowired
    SizeService sizeService;
    @Autowired
    ColorService colorService;
    @Autowired
    DetailProductService detailProductService;
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    CommentService commentService;
    @Autowired
    ModelMapper modelMapper;


    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Product> page1 = productSevice.findPage(page, size);


        return ResponseEntity.ok().body(new IGenericResponse<Page<Product>>(page1, 200, "Page product"));
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody ProductDTO productDTO) {
        if (categoryService.findById(productDTO.getCategoryId()).isPresent()) {
            Product product = new Product();
            product.setVoteAverage(0.0);
            product.setIsDelete(false);
            product.setIsActive(false);
            product.setIsComplete(false);
            product.setProductName(productDTO.getProductName());
            product.setDescription(productDTO.getDescription());
            product.setImage(productDTO.getImage());
            product.setCreateDate(LocalDateTime.now());
            product.setCategory(categoryService.findById(productDTO.getCategoryId()).get());
            return ResponseEntity.ok().body(new IGenericResponse<Product>(productSevice.save(product), 200, "success"));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
        }


    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody ProductUpdateDTO productUpdateDTO) {
        Optional<Product> product1 = productSevice.findById(productUpdateDTO.getId());
        Optional<Category> categoryOptional = categoryService.findById((productUpdateDTO.getCategoryId()));
        if (product1.isPresent() && categoryOptional.isPresent()) {
            product1.get().setProductName(productUpdateDTO.getProductName());
            product1.get().setVoteAverage(productUpdateDTO.getVoteAverage());
            product1.get().setIsDelete(false);
            product1.get().setDescription(productUpdateDTO.getDescription());
            product1.get().setImage(productUpdateDTO.getImage());
            product1.get().setCreateDate(LocalDateTime.now());
            product1.get().setIsDelete(productUpdateDTO.getIsDelete());
            product1.get().setIsActive(productUpdateDTO.getIsActive());
            product1.get().setCategory(categoryOptional.get());
            List<TagProduct> tagProductList = product1.get().getTagProducts();
            List<MaterialProduct> materialProductList = product1.get().getMaterialProducts();
            for (TagProduct tagProduct : tagProductList
            ) {
                tagProductRepository.deleteById(tagProduct.getTagProductPK());
            }
            for (MaterialProduct materialProduct : materialProductList
            ) {
                materialProductRepository.deleteById(materialProduct.getMaterialProductPK());
            }
            productSevice.save(product1.get());
            List<Material> materialList = new ArrayList<>();
            List<Tag> tagList = new ArrayList<>();
            for (Integer materialId : productUpdateDTO.getMaterialProductIds()
            ) {

                Optional<Material> materialOptional = materialService.findById(materialId);
                if (materialOptional.isPresent() && materialOptional.get().getIsActive() == true && materialOptional.get().getIsDeleted() == false) {
                    MaterialProduct materialProduct = new MaterialProduct();
                    materialProduct.setProduct(product1.get());
                    materialProduct.setMaterial(materialOptional.get());
                    materialProduct.setMaterialProductPK(new MaterialProductPK(materialId, product1.get().getId()));
                    materialProduct.setCreateDate(LocalDateTime.now());
                    materialProduct.setIsDeleted(false);
                    materialProduct.setIsActive(true);
                    materialProductList.add(materialProduct);
                    materialProductRepository.save(materialProduct);
                    materialList.add(materialOptional.get());
                }
            }
            for (Integer s : productUpdateDTO.getTagProductIds()
            ) {
                Optional<Tag> tagOptional = tagService.findById(s);
                if (tagOptional.isPresent() && tagOptional.get().getIsActive() == true && tagOptional.get().getIsDelete() == false) {
                    TagProduct tagProduct = new TagProduct();
                    tagProduct.setProduct(product1.get());
                    tagProduct.setTag(tagOptional.get());
                    tagProduct.setTagProductPK(new TagProductPK(s, product1.get().getId()));
                    tagProduct.setCreateDate(LocalDateTime.now());
                    tagProduct.setIsDeleted(false);
                    tagProduct.setIsActive(true);
                    tagProductList.add(tagProduct);
                    tagProductRepository.save(tagProduct);
                    tagList.add(tagOptional.get());
                }

            }
            product1.get().setTagProducts(tagProductList);
            product1.get().setMaterialProducts(materialProductList);
            Product product = productSevice.save(product1.get());
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(product.getId());
            productResponse.setProductName(product.getProductName());
            productResponse.setVoteAverage(product.getVoteAverage());
            productResponse.setIsDelete(product.getIsDelete());
            productResponse.setDescription(product.getDescription());
            productResponse.setImage(product.getImage());
            productResponse.setCreateDate(product.getCreateDate());
            productResponse.setIsActive(product.getIsActive());
            productResponse.setCategory(categoryOptional.get());
            productResponse.setTagList(tagList);
            productResponse.setMaterialList(materialList);
            return ResponseEntity.ok().body(new IGenericResponse<ProductResponse>(productResponse, 200, "success"));
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
            if (materialOptional.isPresent() && materialOptional.get().getIsActive() == true && materialOptional.get().getIsDeleted() == false) {
                materialList.add(materialOptional.get());
            }
        }
        for (Integer s : productRequest.getTagProductIdList()
        ) {
            Optional<Tag> tagOptional = tagService.findById(s);
            if (tagOptional.isPresent() && tagOptional.get().getIsActive() == true && tagOptional.get().getIsDelete() == false) {
                tagList.add(tagOptional.get());
            }

        }


        Product product = new Product();
        product.setVoteAverage(0.0);
        product.setIsDelete(false);
        product.setIsComplete(false);
        product.setProductName(productRequest.getProductName());
        product.setDescription(productRequest.getDescription());
        product.setImage(productRequest.getImage());
        product.setCreateDate(LocalDateTime.now());
        product.setCategory(categoryService.findById(productRequest.getCategoryId()).get());
        product.setIsActive(true);
        productSevice.save(product);
        List<TagProduct> tagProductList = new ArrayList<>();
        List<MaterialProduct> materialProductList = new ArrayList<>();
        if (categoryService.findById(productRequest.getCategoryId()).isPresent()) {
            for (int i = 0; i < tagList.size(); i++) {
                for (int j = 0; j < materialList.size(); j++) {
                    Optional<Material> materialOptional = materialService.findById(j);
                    Optional<Tag> tagOptional = tagService.findById(i);
                    if (materialOptional.isPresent() && tagOptional.isPresent() && materialOptional.get().getIsActive() == true && materialOptional.get().getIsDeleted() == false
                            && tagOptional.get().getIsActive() == true && tagOptional.get().getIsDelete() == false) {
                        MaterialProduct materialProduct = new MaterialProduct
                                (new MaterialProductPK(j, product.getId()),
                                        false, materialOptional.get(), false, LocalDateTime.now(), product);
                        TagProduct tagProduct = new TagProduct(new TagProductPK(i, product.getId()), false, tagOptional.get(), false, product, LocalDateTime.now());
                        materialProductRepository.save(materialProduct);
                        tagProductRepository.save(tagProduct);
                        materialProductList.add(materialProduct);
                        tagProductList.add(tagProduct);
                    }
                }


            }
            product.setTagProducts(tagProductList);
            product.setMaterialProducts(materialProductList);
            Product product1 = productSevice.save(product);
            ProductResponse productResponse = new ProductResponse();
            productResponse.setId(product.getId());
            productResponse.setProductName(product1.getProductName());
            productResponse.setVoteAverage(product1.getVoteAverage());
            productResponse.setIsDelete(product1.getIsDelete());
            productResponse.setDescription(product1.getDescription());
            productResponse.setImage(product1.getImage());
            productResponse.setCreateDate(product1.getCreateDate());
            productResponse.setIsActive(product1.getIsActive());
            productResponse.setCategory(product.getCategory());
            productResponse.setTagList(tagList);
            productResponse.setMaterialList(materialList);
            productResponse.setIsComplete(false);
            return ResponseEntity.ok().body(new IGenericResponse<>(productResponse, 200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not exists"));

        }


    }

    @GetMapping("findOptionProductById")
    public ResponseEntity<?> findOptionProductById(@RequestParam("id") Integer id) {
        Optional<Product> productOptional = productSevice.findById(id);
        if (productOptional.isPresent()) {
//            ProductHandleValue productHandleValue = productSevice.findOptionByProductId(id);
//            ProductOptionalDTO productOptionalDTO = new ProductOptionalDTO(productHandleValue.getId(),
//                    productHandleValue.getDescription(), productHandleValue.getIsActive(), productHandleValue.getMaxPrice(), productHandleValue.getMinPrice()
//                    , productHandleValue.getProductName(), null);
//
//            List<DetailProduct> detailProducts = detailProductService.findAllByProductId(id);
//            Set<Integer> colorIdList = detailProducts.stream().map(e -> e.getColor().getId()).collect(Collectors.toSet());
//            Set<Integer> sizeIdList = detailProducts.stream().map(e -> e.getSize().getId()).collect(Collectors.toSet());
//            List<ValueOption> colorOptionList = new ArrayList<>();
//
//            for (Integer x : colorIdList
//            ) {
//                Optional<Color> color = colorService.findById(x);
//                ValueOption colorOption = new ValueOption();
//                colorOption.setId(color.get().getId());
//                colorOption.setName(color.get().getColorName());
//                colorOptionList.add(colorOption);
//
//            }
//            OptionProduct optionColorProduct = new OptionProduct("Color", colorOptionList);
//            List<ValueOption> sizeOptionList = new ArrayList<>();
//
//
//            for (Integer x : sizeIdList
//            ) {
//                Optional<Size> sizeOptional = sizeService.findById(x);
//                ValueOption sizeOption = new ValueOption();
//                sizeOption.setId(sizeOptional.get().getId());
//                sizeOption.setName(sizeOptional.get().getSizeName());
//                sizeOptionList.add(sizeOption);
//
//            }
//
//            OptionProduct optionSizeProduct = new OptionProduct("Size", sizeOptionList);
//            //
//            List<Integer> listTagId= tagProductRepository.findTagIdByProductId(id);
//            List<Integer> listMaterialId=materialProductRepository.findMaterialIdByProductId(id);
//            List<ValueOption> materialOptionList = new ArrayList<>();
//
//
//            for (Integer x: listMaterialId
//            ) {
//                Optional<Material> materialOptional = materialService.findById(x);
//                ValueOption materialOption = new ValueOption();
//                materialOption.setId(materialOptional.get().getId());
//                materialOption.setName(materialOptional.get().getMaterialName());
//                materialOptionList.add(materialOption);
//            }
//            OptionProduct optionMaterialProduct = new OptionProduct("Material", materialOptionList);
//            List<ValueOption> tagOptionList = new ArrayList<>();
//            for (Integer x: listTagId
//            ) {
//                Optional<Tag> tagOptional = tagService.findById(x);
//                ValueOption tagOption = new ValueOption();
//                tagOption.setId(tagOptional.get().getId());
//                tagOption.setName(tagOptional.get().getTagName());
//                tagOptionList.add(tagOption);
//            }
//            OptionProduct optionTagProduct = new OptionProduct("Tag", tagOptionList);
//
//
//            List<OptionProduct> optionProducts = new ArrayList<>();
//            optionProducts.add(optionSizeProduct);
//            optionProducts.add(optionColorProduct);
//            optionProducts.add(optionMaterialProduct);
//            optionProducts.add(optionTagProduct);
//            productOptionalDTO.setOptions(optionProducts);
//            return ResponseEntity.ok().body(new IGenericResponse<>(productOptionalDTO, 200, ""));
//        }
            List<Integer> listTagId= tagProductRepository.findTagIdByProductId(id);
            List<Integer> listMaterialId=materialProductRepository.findMaterialIdByProductId(id);
            List<DetailProduct> detailProductList= detailProductService.findAllByProductId(id);
            List<Tag> tagList= new ArrayList<>();
            List<Material> materialList= new ArrayList<>();
            List<Color>colorList= new ArrayList<>();
            List<Size> sizeList= new ArrayList<>();
            for (DetailProduct dp: detailProductList
                 ) {
                Color c= colorService.findByDetailProductId(dp.getId());
                Size s = sizeService.findByDetailProductId(dp.getId());
                colorList.add(c);
                sizeList.add(s);
            }
            for (Integer x :listTagId
                 ) {
                Optional<Tag> tagOptional=tagService.findById(x);
                tagList.add(tagOptional.get());

            }
            for (Integer x :listMaterialId
            ) {
                Optional<Material> materialOptional=materialService.findById(x);
                materialList.add(materialOptional.get());

            }

            OptionalProduct optionalProduct= new OptionalProduct(tagList,materialList,colorList,sizeList);
return ResponseEntity.ok().body(new IGenericResponse<>(optionalProduct,200,""));

        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));

    }

    @DeleteMapping("deleteByListId")
    public ResponseEntity<?> deleteArrayTagId(@RequestBody ListProductIdDTO listProductIdDTO) {
        List<Integer> list = listProductIdDTO.getListProductId();


        if (list.size() > 0) {
            for (Integer x : list
            ) {
                Optional<Product> productOptional = productSevice.findById(x);

                if (productOptional.isPresent()) {
                    favoriteService.deleteByProductId(x);
                    commentService.deleteByProductId(x);
                    materialService.deleteByProductId(x);
                    materialService.deleteByProductId(x);
                    tagService.deleteByProductId(x);
                    detailProductService.deleteByProductId(x);
                    productSevice.deleteById(x);
                }
            }
            return ResponseEntity.ok().body(new IGenericResponse<>("", 200, ""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found "));
    }
}
